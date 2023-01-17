package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Message;
import net.example.virtualoffice.virtualoffice.model.TakenFor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class WriteMessageDTO {
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
    public Message bindMessagesWithMembers(){
        var message = new Message();
        message.setCompanyId(companyId);
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
}
