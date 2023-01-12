package net.example.virtualoffice.virtualoffice.model.projection;

import net.example.virtualoffice.virtualoffice.model.Message;

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

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(final String fromName) {
        this.fromName = fromName;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(final String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(final String fromPhone) {
        this.fromPhone = fromPhone;
    }
}
