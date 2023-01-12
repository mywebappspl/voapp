package net.example.virtualoffice.virtualoffice.model.projection;

import net.example.virtualoffice.virtualoffice.model.Message;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class ReadMessageDTO {
    private int messageId;
    private int messageForCompany;
    private String content;
    private LocalDateTime takenOn;
    private Set<ReadMemberBasicInfoDto> members;

    public String getContent() {
        return content;
    }

    public ReadMessageDTO(final Message message/*, final List<TakenFor> takenFor*/) {
        this.messageId = message.getId();
        this.messageForCompany = message.getCompany_id();
        this.takenOn = message.getCreatedOn();
        this.content = message.getContent();
        this.members = message.getTakenForMembers().stream().map(m->new ReadMemberBasicInfoDto(m.getMember().getId(),m.getMember().getName(),m.getMember().getEmail(),m.getMember().getPhone(),m.getMember().isActive())).collect(Collectors.toSet());
    }
    public int getMessageId() {
        return messageId;
    }

    public int getMessageForCompany() {
        return messageForCompany;
    }

    public LocalDateTime getTakenOn() {
        return takenOn;
    }

    public Set<ReadMemberBasicInfoDto> getMembers() {
        return members;
    }
}
