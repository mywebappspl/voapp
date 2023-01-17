package net.example.virtualoffice.virtualoffice.services;

import net.example.virtualoffice.virtualoffice.exception.CustomExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.ExceptionMessages;
import net.example.virtualoffice.virtualoffice.model.Member;
import net.example.virtualoffice.virtualoffice.model.TakenFor;
import net.example.virtualoffice.virtualoffice.repository.MembersRepository;
import net.example.virtualoffice.virtualoffice.repository.TakenForRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.example.virtualoffice.virtualoffice.model.projection.MemberStatus;
import net.example.virtualoffice.virtualoffice.model.projection.ReadMemberDto;
import net.example.virtualoffice.virtualoffice.model.projection.ReadMemberMessgesDTO;


@Service
public class MemberService {
    private final MembersRepository membersRepository;
    private final TakenForRepository takenForRepository;


    MemberService(final MembersRepository membersRepository, final TakenForRepository takenForRepository) {
        this.membersRepository = membersRepository;
        this.takenForRepository = takenForRepository;
    }

    private boolean IsMemberNameExists(String name, int companyId) {
        return membersRepository.existsByName(name, companyId) == 1;

    }

    public ReadMemberDto createMember(int companyId, Member member) {
        if (!IsMemberNameExists(member.getName(), companyId)) {
            member.setActive(true);
            member.setCompanyId(companyId);
            Member result = membersRepository.save(member);
            return new ReadMemberDto(result);
        } else {
            throw new CustomExceptionHandler(ExceptionMessages.MEMBER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public Member updateMember(int companyId, int eId, Member member) {

        return membersRepository.findById(eId)
                .map(m -> {
                    m.setName(member.getName());
                    m.setPhone(member.getPhone());
                    m.setEmail(member.getEmail());
                    m.setCompanyId(companyId);
                    return membersRepository.save(m);

                }).orElseThrow(() -> new CustomExceptionHandler(ExceptionMessages.MEMBER_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
    }

    public ReadMemberMessgesDTO readMessagesForMember(int id, Pageable pageable) {
        Page<TakenFor> result = takenForRepository.findAllByMemberId(id, pageable);
        return new ReadMemberMessgesDTO(id, result);
    }

    public Member getMemberService(int id) {
        return membersRepository.findById(id).orElseThrow(() -> new CustomExceptionHandler(ExceptionMessages.MEMBER_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
    }

    public MemberStatus changeMemberStatus(int id, MemberStatus status) {
        Member member = membersRepository.findById(id)
                .map(m -> {
                    m.setActive(status.isActive());
                    return membersRepository.save(m);
                }).orElseThrow(() -> new CustomExceptionHandler(ExceptionMessages.MEMBER_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
        return new MemberStatus(member.isActive());
    }
}