package net.example.virtualoffice.virtualoffice.services;

import net.example.virtualoffice.virtualoffice.exception.MemberExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.MemberNameNotExceptionHandler;
import net.example.virtualoffice.virtualoffice.model.Member;
import net.example.virtualoffice.virtualoffice.model.TakenFor;
import net.example.virtualoffice.virtualoffice.repository.MembersRepository;
import net.example.virtualoffice.virtualoffice.repository.TakenForRepository;
import net.example.virtualoffice.virtualoffice.services.MessagesForLogs.LogMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.example.virtualoffice.virtualoffice.model.projection.MemberStatus;
import net.example.virtualoffice.virtualoffice.model.projection.ReadMemberDto;
import net.example.virtualoffice.virtualoffice.model.projection.ReadMemberMessgesDTO;


@Service
public class MemberService {
    private final MembersRepository membersRepository;
    private final TakenForRepository takenForRepository;
    private final LogService logService;

    MemberService(final MembersRepository membersRepository, final TakenForRepository takenForRepository, final LogService logService) {
        this.membersRepository = membersRepository;
        this.takenForRepository = takenForRepository;
        this.logService = logService;
    }

    private boolean IsMemberNameExists(String name, int companyId) {
        return membersRepository.existsByName(name, companyId) == 1;

    }

    public ReadMemberDto createMember(int companyId, Member member) {
        if (!IsMemberNameExists(member.getName(), companyId)) {
            member.setActive(true);
            member.setCompany_id(companyId);
            Member result = membersRepository.save(member);
            logService.addAgentId(1);
            logService.addCompanyId(member.getCompany_id());
            logService.addMemberName(member.getName());
            logService.SaveLog(LogMessages.USER_IN_COMPANY_CREATED);
            return new ReadMemberDto(result);
        } else {
            throw new MemberExceptionHandler("In company ID: " + member.getCompany_id() + "member " + member.getName() + " already exists");
        }
    }

    @Transactional
    public Member updateMember(int companyId, int eId, Member member) {

        return membersRepository.findById(eId)
                .map(m -> {
                    m.setName(member.getName());
                    m.setPhone(member.getPhone());
                    m.setEmail(member.getEmail());
                    m.setCompany_id(companyId);
                    return membersRepository.save(m);

                }).orElseThrow(() -> new MemberNameNotExceptionHandler(" company id " + member.getCompany_id()));

    }

    public ReadMemberMessgesDTO readMessagesForMember(int id, Pageable pageable) {
        Page<TakenFor> result = takenForRepository.findAllByMember_Id(id, pageable);
        return new ReadMemberMessgesDTO(id, result);
    }

    public Member getMemberService(int id) {
        return membersRepository.findById(id).orElseThrow(MemberNameNotExceptionHandler::new);
    }

    public MemberStatus changeMemberStatus(int id, MemberStatus status) {
        Member member = membersRepository.findById(id)
                .map(m -> {
                    m.setActive(status.isActive());
                    return membersRepository.save(m);
                }).orElseThrow(MemberExceptionHandler::new);
        return new MemberStatus(member.isActive());
    }
}