package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Message;
@Setter
@Getter
public class ReadBasicMessageInfoDTO {
    private int id;
    private String fromName;
    private String fromEmail;
    private String fromPhone;
    private String content;

    public ReadBasicMessageInfoDTO(final Message message) {
        this.id = message.getId();
        this.fromName = message.getFromName();
        this.fromEmail = message.getFromEmail();
        this.fromPhone = message.getFromPhone();
        this.content = message.getContent();
    }
}
