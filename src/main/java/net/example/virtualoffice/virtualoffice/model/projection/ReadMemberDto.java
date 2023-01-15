package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Member;
@Getter
@Setter
public class ReadMemberDto {
    private final int id;
    private final String name;
    private final String phone;
    private final String email;
    private final boolean active;

    public ReadMemberDto(final Member source) {
        this.id=source.getId();
        this.name=source.getName();
        this.email=source.getEmail();
        this.phone= source.getPhone();
        this.active= source.isActive();

    }
}
