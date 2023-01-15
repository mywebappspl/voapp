package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReadCompanyMessageForExport {
    private LocalDateTime created_on;

    private String fromName;
    private String fromEmail;
    private String fromPhone;
    private String content;
    private String memberName;

    public ReadCompanyMessageForExport(LocalDateTime created_on, String fromName, String fromEmail, String fromPhone, String content, String memberName){
        this.created_on=created_on;
        this.fromName=fromName;
        this.fromEmail=fromEmail;
        this.fromPhone=fromPhone;
        this.content=content;
        this.memberName=memberName;
    }

}
