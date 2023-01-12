package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadMemberBasicInfoDto {
    private int id;
    private String name;
    private String email;
    private String phone;
    private boolean active;
    ReadMemberBasicInfoDto(final int id, final String name, final String email, final String phone, final boolean active) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.active = active;
    }
}
