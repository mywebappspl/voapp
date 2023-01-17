package net.example.virtualoffice.virtualoffice.services;

import lombok.AllArgsConstructor;
import net.example.virtualoffice.virtualoffice.Utils.Voutils;
import net.example.virtualoffice.virtualoffice.exception.CustomExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.ExceptionMessages;
import net.example.virtualoffice.virtualoffice.model.Company;
import net.example.virtualoffice.virtualoffice.model.Member;
import net.example.virtualoffice.virtualoffice.model.Message;
import net.example.virtualoffice.virtualoffice.model.projection.*;
import net.example.virtualoffice.virtualoffice.repository.CompaniesRepository;
import net.example.virtualoffice.virtualoffice.repository.MessagesRopository;
import net.example.virtualoffice.virtualoffice.repository.TakenForRepository;
import net.example.virtualoffice.virtualoffice.services.utils.CSVExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {
    final private CompaniesRepository companiesRepository;
    final private MessagesRopository messagesRopository;
    final private TakenForRepository takenForRepository;
    final private CSVExport csvExport;

    private boolean isCompanyNameExists(String companyName) {
        return companiesRepository.existsByCompanyName(companyName) != 1;
    }

    private boolean companyMessagesExportOrDisplayValidation(int id, Date startDate, Date endDate) {
        if (!companiesRepository.existsById(id))
            throw new CustomExceptionHandler(ExceptionMessages.COMPANY_WITH_ID_NOT_EXISTS, HttpStatus.NOT_FOUND);
        if ((startDate != null && endDate == null) || (startDate == null && endDate != null)) {
            throw new CustomExceptionHandler(ExceptionMessages.EXPORT_CSV_TWO_DATES_REQUIRED, HttpStatus.BAD_REQUEST);
        }
        if (startDate != null) {
            if (startDate.compareTo(endDate) > 0)
                throw new CustomExceptionHandler(ExceptionMessages.EXPORT_CSV_FIRST_DATE_LATE, HttpStatus.BAD_REQUEST);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Company createCompany(Company source) {
        if (isCompanyNameExists(source.getCompanyName())) {
            Company company = new Company();
            company.setCompanyName(source.getCompanyName());
            company.setPhone(source.getPhone());
            company.setEmail(source.getEmail());
            company.setActive(true);
            companiesRepository.save(company);
            company.setMembers(source.getMembers().stream().map(m -> new Member(m.getName(), m.getPhone(), m.getEmail(), company.getId())).collect(Collectors.toSet()));
            return company;
        } else {
            throw new CustomExceptionHandler(ExceptionMessages.COMPANY_NAME_EXISTS, HttpStatus.NOT_FOUND);
        }
    }

    public ReadCompanyWithBasicUserInfoDTO readCompany(int id, Boolean active) {
        Optional<Company> company = companiesRepository.findById(id);
        if (company.isPresent())
            return new ReadCompanyWithBasicUserInfoDTO(company.get(), active);
        else
            throw new CustomExceptionHandler(ExceptionMessages.COMPANY_WITH_ID_NOT_EXISTS, HttpStatus.NOT_FOUND);
    }

    public List<?> readAllCompanies(String show, String extended) {
        List<Company> companies = switch (show) {
            case "active" -> companiesRepository.findCompanyByStatus(true);
            case "inactive" -> companiesRepository.findCompanyByStatus(false);
            default -> companiesRepository.findAll();
        };
        if (extended.equals("true"))
            return companies.stream().map(ReadCompanyDto::new).collect(Collectors.toList());
        else
            return companies.stream().map(ReadBasicCompanyDTO::new).collect(Collectors.toList());

    }

    public ReadCompanyMessages readMessagesForCompany(int id, Date startDate, Date endDate, Pageable pageable) {
        Page<Message> message;

        if (companyMessagesExportOrDisplayValidation(id, startDate, endDate)) {
            message = messagesRopository.findAllMessagesWithDatesByCompanyId(id, startDate, Date.from(Voutils.endDateCalculation(endDate)), pageable);
        } else {
            message = messagesRopository.findAllMessagesByCompanyId(id, pageable);
        }
        return new ReadCompanyMessages(id, message);
    }

    public boolean exportMessagesForCompany(HttpServletResponse servletResponse, int id, Date startDate, Date endDate) {
        List<ReadCompanyMessageForExport> messagesToExport;
        if (companyMessagesExportOrDisplayValidation(id, startDate, endDate)) {

            messagesToExport = takenForRepository.
                    findAllMessagesByDate(id, startDate, Date.from(Voutils.endDateCalculation(endDate)))
                    .stream().map(m -> new ReadCompanyMessageForExport(m.getMessage().getCreatedOn(), m.getMessage().getFromName(), m.getMessage().getFromEmail(), m.getMessage().getFromPhone(), m.getMessage().getContent(), m.getMember().getName()))
                    .collect(Collectors.toList());
        } else {
            messagesToExport = takenForRepository.findAllMessages(id).stream().map(m -> new ReadCompanyMessageForExport(m.getMessage().getCreatedOn(), m.getMessage().getFromName(), m.getMessage().getFromEmail(), m.getMessage().getFromPhone(), m.getMessage().getContent(), m.getMember().getName())).collect(Collectors.toList());
        }
        if (messagesToExport.size() == 0) {
            throw new CustomExceptionHandler(ExceptionMessages.NO_MESSAGES, HttpStatus.NOT_FOUND);
        } else {
            return csvExport.exportToCSV(servletResponse, id, messagesToExport);
        }
    }

    @Transactional
    public void UpdateCompanyService(int id, Company company) {
        if (isCompanyNameExists(company.getCompanyName())) {
            companiesRepository.findById(id)
                    .map(cmp -> {
                        cmp.setCompanyName(company.getCompanyName());
                        cmp.setEmail(company.getEmail());
                        cmp.setPhone(company.getPhone());
                        cmp.setActive(company.isActive());
                        return companiesRepository.save(cmp);
                    }).orElseThrow(() -> new CustomExceptionHandler(ExceptionMessages.COMPANY_WITH_ID_NOT_EXISTS, HttpStatus.NOT_FOUND));
        } else {
            throw new CustomExceptionHandler(ExceptionMessages.COMPANY_NAME_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }

    public CompanyStatus changeCompanyStatus(int id, CompanyStatus status) {
        Company company = companiesRepository.findById(id)
                .map(m -> {
                    m.setActive(status.isActive());
                    return companiesRepository.save(m);
                }).orElseThrow(() -> new CustomExceptionHandler(ExceptionMessages.COMPANY_WITH_ID_NOT_EXISTS, HttpStatus.NOT_FOUND));
        return new CompanyStatus(company.isActive());
    }

    public CompanyWithMemberIdAndNameDTO SimplyCompanyInfoWithMemberIdAndName(int id) {
        Company company = companiesRepository.findById(id).orElseThrow(() -> new CustomExceptionHandler(ExceptionMessages.COMPANY_WITH_ID_NOT_EXISTS, HttpStatus.NOT_FOUND));
        if (!company.isActive())
            throw new CustomExceptionHandler(ExceptionMessages.COMPANY_IS_INACTIVE, HttpStatus.BAD_REQUEST);
        return new CompanyWithMemberIdAndNameDTO(company);
    }
}
