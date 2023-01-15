package net.example.virtualoffice.virtualoffice.model.projection;

import net.example.virtualoffice.virtualoffice.model.Company;
import net.example.virtualoffice.virtualoffice.model.Member;

import java.util.Set;
import java.util.stream.Collectors;

public class ReadCompanyWithBasicUserInfoDTO extends ReadCompanyDto{
    public ReadCompanyWithBasicUserInfoDTO(final Company source, Boolean activeMembers) {

        super(source);
        if (activeMembers) {
            this.members = source.getMembers().stream()
                    .filter(Member::isActive)
                    .map(m -> new ReadMemberBasicInfoDto(m.getId(), m.getName(), m.getEmail(), m.getPhone(),m.isActive())).collect(Collectors.toSet());
        } else {
            this.members = source.getMembers().stream()
                    .map(m -> new ReadMemberBasicInfoDto(m.getId(), m.getName(), m.getEmail(), m.getPhone(),m.isActive())).collect(Collectors.toSet());
        }
    }
    private final Set<ReadMemberBasicInfoDto> members;
    public Set<ReadMemberBasicInfoDto> getMembers() {
        return members;
    }
}
