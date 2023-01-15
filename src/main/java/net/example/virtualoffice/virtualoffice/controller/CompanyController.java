package net.example.virtualoffice.virtualoffice.controller;

import net.example.virtualoffice.virtualoffice.model.Company;
import net.example.virtualoffice.virtualoffice.model.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.example.virtualoffice.virtualoffice.model.projection.CompanyStatus;
import net.example.virtualoffice.virtualoffice.model.projection.CompanyWithMemberIdAndNameDTO;
import net.example.virtualoffice.virtualoffice.model.projection.ReadCompanyWithBasicUserInfoDTO;
import net.example.virtualoffice.virtualoffice.model.projection.ReadMemberDto;
import net.example.virtualoffice.virtualoffice.services.CompanyService;
import net.example.virtualoffice.virtualoffice.services.MemberService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/company")
class CompanyController {
    private final CompanyService companyService;
    private final MemberService memberService;

    CompanyController(final CompanyService companyService, MemberService memberService) {
        this.companyService = companyService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody @Valid Company company) {
        return ResponseEntity.created(URI.create("/company/" + company.getId()))
                .body(companyService.createCompany(company));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCompany(@PathVariable int id, @Valid @RequestBody final Company company) {
        companyService.UpdateCompanyService(id, company);
    }

    @GetMapping
    public ResponseEntity<List<?>> readAllCompanies(
            @RequestParam(name = "show", defaultValue = "all", required = false) String show,
            @RequestParam(name = "extended", defaultValue = "false", required = false) String extended) {
        return ResponseEntity.ok(companyService.readAllCompanies(show, extended));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCompanyWithBasicUserInfoDTO> readCompany(
            @PathVariable int id,
            @RequestParam(name = "activemembers", defaultValue = "true", required = false) Boolean activemembers
    ) {
        return ResponseEntity.ok(companyService.readCompany(id, activemembers));
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<?> readCompanyMessages(
            HttpServletResponse servletResponse,
            @PathVariable int id,
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "10", required = false) int itemsPerPage,
            @RequestParam(name = "export", defaultValue = "false", required = false) boolean export,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate

    ) {

        if (!export) {
            Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(Sort.Direction.ASC, "id"));
            return ResponseEntity.ok(companyService.readMessagesForCompany(id, startDate, endDate, pageable));
        } else {
            if (companyService.exportMessagesForCompany(servletResponse, id, startDate, endDate))
                return ResponseEntity.ok().build();
            else
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @PatchMapping("/{id}")
    public CompanyStatus setCompanyStatus(@PathVariable int id, @RequestBody CompanyStatus status) {
        return companyService.changeCompanyStatus(id, status);
    }

    @GetMapping("{id}/members")
    public CompanyWithMemberIdAndNameDTO simplyCompanyInfoWithMemberIdAndName(@PathVariable int id) {
        return companyService.SimplyCompanyInfoWithMemberIdAndName(id);
    }

    @PostMapping("{id}/employee")
    ResponseEntity<?> createMember(@PathVariable int id, @RequestBody @Valid Member member) {
        ReadMemberDto readMemberDto = memberService.createMember(id, member);
        return ResponseEntity.created(URI.create("/" + readMemberDto.getId())).body(readMemberDto);

    }

    @PutMapping("{id}/employee/{eid}")
    ResponseEntity<Member> updateMember(@PathVariable int id, @PathVariable int eid, @RequestBody @Valid Member member) {
        return ResponseEntity.accepted().body(memberService.updateMember(id, eid, member));
    }
}
