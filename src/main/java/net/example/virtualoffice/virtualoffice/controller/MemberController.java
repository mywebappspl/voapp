package net.example.virtualoffice.virtualoffice.controller;

import net.example.virtualoffice.virtualoffice.model.Member;
import net.example.virtualoffice.virtualoffice.repository.MembersRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.example.virtualoffice.virtualoffice.model.projection.MemberStatus;
import net.example.virtualoffice.virtualoffice.model.projection.ReadMemberMessgesDTO;
import net.example.virtualoffice.virtualoffice.services.MemberService;

@RestController
@CrossOrigin
@RequestMapping("/member")
class MemberController {
    private MembersRepository membersRepository;
    private MemberService memberService;
    MemberController(final MembersRepository membersRepository, final MemberService memberService) {
        this.membersRepository = membersRepository;
        this.memberService=memberService;
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<ReadMemberMessgesDTO> readMemberMessages(@PathVariable int id, @RequestParam int pageNumber, @RequestParam int itemsPerPage)
    {
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(Sort.Direction.ASC,"id"));
        ReadMemberMessgesDTO messages= memberService.readMessagesForMember(id,pageable);
        return ResponseEntity.ok(messages);

    }
    @GetMapping("{id}")
    public Member getMember(@PathVariable int id)
    {
        return memberService.getMemberService(id);
    }

    @PatchMapping("/{id}")
    public MemberStatus setMemberStatus(@PathVariable  int id, @RequestBody MemberStatus status)
    {
        return memberService.changeMemberStatus(id, status);
    }
}
