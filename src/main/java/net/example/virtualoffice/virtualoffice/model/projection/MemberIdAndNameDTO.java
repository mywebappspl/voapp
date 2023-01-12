package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberIdAndNameDTO {
    private int memberId;
    private String name;
    public MemberIdAndNameDTO(int id, String name){
        this.memberId=id;
        this.name=name;
    }
}
