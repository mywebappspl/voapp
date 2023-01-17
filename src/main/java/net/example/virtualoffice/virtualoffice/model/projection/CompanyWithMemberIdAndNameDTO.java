package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Company;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CompanyWithMemberIdAndNameDTO {
    private int companyId;
    private String companyName;
    private List<MemberIdAndNameDTO> members;

    public CompanyWithMemberIdAndNameDTO(Company company)
    {
        this.companyId=company.getId();
        this.companyName=company.getCompanyName();

        this.members=company.getMembers().stream()
                .filter(m->m.isActive()==true)
                .map(m->new MemberIdAndNameDTO(m.getId(),m.getName()))
                .collect(Collectors.toList());
    }

}

