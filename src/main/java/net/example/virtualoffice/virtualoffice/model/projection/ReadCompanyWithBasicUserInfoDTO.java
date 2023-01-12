package net.example.virtualoffice.virtualoffice.model.projection;

import net.example.virtualoffice.virtualoffice.model.Company;

import java.util.Set;
import java.util.stream.Collectors;

public class ReadCompanyWithBasicUserInfoDTO extends ReadCompanyDto{
    public ReadCompanyWithBasicUserInfoDTO(final Company source, Boolean activemembers) {

        super(source);
        if (activemembers) {
            this.members = source.getMembers().stream()
                    .filter(f -> f.isActive() == true)
                    .map(m -> new ReadMemberBasicInfoDto(m.getId(), m.getName(), m.getEmail(), m.getPhone(),m.isActive())).collect(Collectors.toSet());
        } else {
            this.members = source.getMembers().stream()
                    .map(m -> new ReadMemberBasicInfoDto(m.getId(), m.getName(), m.getEmail(), m.getPhone(),m.isActive())).collect(Collectors.toSet());
        }
    }
    private Set<ReadMemberBasicInfoDto> members;
    public Set<ReadMemberBasicInfoDto> getMembers() {
        return members;
    }
}
