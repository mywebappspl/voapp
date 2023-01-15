package net.example.virtualoffice.virtualoffice.services;

import net.example.virtualoffice.virtualoffice.Utils.voutils;
import net.example.virtualoffice.virtualoffice.exception.CompanyExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.CompanyMessagesNotFoundExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.CompanyNameExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.ExportToCSVExceptionHandler;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    final private CompaniesRepository companiesRepository;
    final private MessagesRopository messagesRopository;
    final private TakenForRepository takenForRepository;
    final private CSVExport csvExport;

    CompanyService(final CompaniesRepository companiesRepository, final MessagesRopository messagesRopository, final TakenForRepository takenForRepository, final CSVExport csvExport) {
        this.companiesRepository = companiesRepository;
        this.messagesRopository = messagesRopository;
        this.takenForRepository = takenForRepository;
        this.csvExport = csvExport;
    }

    private boolean isCompanyNameExists(String companyName) {
        return companiesRepository.existsByCompanyName(companyName) != 1;
    }

    private boolean CompanyMessagesExportOrDisplayValidation(int id, Date startDate, Date endDate) {
        if (!companiesRepository.existsById(id))
            throw new CompanyExceptionHandler();
        if ((startDate != null && endDate == null) || (startDate == null && endDate != null)) {
            throw new ExportToCSVExceptionHandler(" This endpoint requires two dates for company id: " + id);
        }
        if (startDate != null) {
            if (startDate.compareTo(endDate) > 0)
                throw new ExportToCSVExceptionHandler(" Start date is higher than end date for company id:" + id);
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
            throw new CompanyNameExceptionHandler();
        }
    }

    public ReadCompanyWithBasicUserInfoDTO readCompany(int id, Boolean active) {
        Company company = companiesRepository.findById(id).orElseThrow(CompanyExceptionHandler::new);
        return new ReadCompanyWithBasicUserInfoDTO(company, active);
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

        if (CompanyMessagesExportOrDisplayValidation(id, startDate, endDate)) {

            message = messagesRopository.findAllMessagesWithDatesByCompany_Id(id, startDate, Date.from(voutils.EndDateCalculation(endDate)), pageable);
        } else {
            message = messagesRopository.findAllMessagesByCompany_Id(id, pageable);
        }
        return new ReadCompanyMessages(id, message);
    }

    public boolean exportMessagesForCompany(HttpServletResponse servletResponse, int id, Date startDate, Date endDate) {
        List<ReadCompanyMessageForExport> messagesToExport;
        if (CompanyMessagesExportOrDisplayValidation(id, startDate, endDate)) {

            messagesToExport = takenForRepository.
                    findAllMessagesByDate(id, startDate, Date.from(voutils.EndDateCalculation(endDate)))
                    .stream().map(m -> new ReadCompanyMessageForExport(m.getMessage().getCreatedOn(), m.getMessage().getFromName(), m.getMessage().getFromEmail(), m.getMessage().getFromPhone(), m.getMessage().getContent(), m.getMember().getName()))
                    .collect(Collectors.toList());
        } else {
            messagesToExport = takenForRepository.findAllMessages(id).stream().map(m -> new ReadCompanyMessageForExport(m.getMessage().getCreatedOn(), m.getMessage().getFromName(), m.getMessage().getFromEmail(), m.getMessage().getFromPhone(), m.getMessage().getContent(), m.getMember().getName())).collect(Collectors.toList());
        }
        if (messagesToExport.size() == 0) {
            throw new CompanyMessagesNotFoundExceptionHandler();
        } else {
            return csvExport.ExportToCSV(servletResponse, id, messagesToExport);
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
                    }).orElseThrow(CompanyExceptionHandler::new);
        } else {
            throw new CompanyNameExceptionHandler();
        }
    }

    public CompanyStatus changeCompanyStatus(int id, CompanyStatus status) {
        Company company = companiesRepository.findById(id)
                .map(m -> {
                    m.setActive(status.isActive());
                    return companiesRepository.save(m);
                }).orElseThrow(CompanyExceptionHandler::new);
        return new CompanyStatus(company.isActive());
    }

    public CompanyWithMemberIdAndNameDTO SimplyCompanyInfoWithMemberIdAndName(int id) {
        Company company = companiesRepository.findById(id).orElseThrow(CompanyExceptionHandler::new);
        if (!company.isActive())
            throw new CompanyExceptionHandler();
        return new CompanyWithMemberIdAndNameDTO(company);
    }
}
