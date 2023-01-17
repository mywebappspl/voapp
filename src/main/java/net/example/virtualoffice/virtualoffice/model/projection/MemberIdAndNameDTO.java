package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberIdAndNameDTO {
    private int memberId;
    private String name;
}
