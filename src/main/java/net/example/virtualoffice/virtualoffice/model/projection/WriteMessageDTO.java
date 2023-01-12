package net.example.virtualoffice.virtualoffice.model.projection;

import net.example.virtualoffice.virtualoffice.model.Message;
import net.example.virtualoffice.virtualoffice.model.TakenFor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WriteMessageDTO {
    private int id;
    @NotBlank
    private String fromName;
    @Email
    private String fromEmail;
    @NotBlank
    private String fromPhone;
    @NotBlank
    private String content;
    @NotNull
    private List<Integer> members;
    @NotNull
    private int companyId;

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public void setMembers(final List<Integer> members) {
        this.members = members;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final int companyId) {
        this.companyId = companyId;
    }
    public Message bindMessagesWithMembers(){
        var message = new Message();
        message.setCompany_id(companyId);
        message.setFromName(fromName);
        message.setFromEmail(fromEmail);
        message.setFromPhone(fromPhone);
        message.setContent(this.content);
        message.setCreatedOn(LocalDateTime.now());
        message.setTakenForMembers(
                members.stream().map(m-> new TakenFor(message,m)).collect(Collectors.toSet())
        );
        return message;
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
