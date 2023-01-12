package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReadMessagesDTO {
    private int id;
    private String fromName;
    private String fromEmail;
    private String fromPhone;
    private String content;
    private LocalDateTime takenOn;

    public ReadMessagesDTO(final int id, final String fromName, final String fromEmail, final String fromPhone, final String content, final LocalDateTime takenOn) {
        this.id = id;
        this.fromName=fromName;
        this.fromEmail=fromEmail;
        this.fromPhone=fromPhone;
        this.content = content;
        this.takenOn = takenOn;
    }

}
