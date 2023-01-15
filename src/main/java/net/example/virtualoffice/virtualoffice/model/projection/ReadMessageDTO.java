package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Message;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@Setter
public class ReadMessageDTO {
    private final int messageId;
    private final int messageForCompany;
    private final String content;
    private final LocalDateTime takenOn;
    private final Set<ReadMemberBasicInfoDto> members;

    public ReadMessageDTO(final Message message/*, final List<TakenFor> takenFor*/) {
        this.messageId = message.getId();
        this.messageForCompany = message.getCompany_id();
        this.takenOn = message.getCreatedOn();
        this.content = message.getContent();
        this.members = message.getTakenForMembers().stream().map(m->new ReadMemberBasicInfoDto(m.getMember().getId(),m.getMember().getName(),m.getMember().getEmail(),m.getMember().getPhone(),m.getMember().isActive())).collect(Collectors.toSet());
    }

}
