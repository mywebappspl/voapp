package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReadMemberBasicInfoDto {
    private int id;
    private String name;
    private String email;
    private String phone;
    private boolean active;
}
