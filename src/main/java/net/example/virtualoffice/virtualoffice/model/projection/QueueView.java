package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.Utils.voutils;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class QueueView {
    private int id;
    private int messageId;
    private String fromName;
    private int companyId;
    private String name;
    private String email;
    private String msgStatus;
    private LocalDateTime queuedOn;
    private String readStatus(int statusId)
    {
        return switch (statusId) {
            case 0 -> "Taken";
            case 1 -> "Queued";
            case 2 -> "QueuedErr";
            case 3 -> "Sent";
            case 4 -> "SendingErr";
            default -> "Undefined";
        };
    }
    public QueueView(final int id, final int messageId, final String fromName, final int companyId, final String name, final String email, final int msgStatus, final Date queuedOn) {
        this.id=id;
        this.messageId = messageId;
        this.fromName = fromName;
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.msgStatus = readStatus(msgStatus);
        this.queuedOn = voutils.convertToLocalDateTimeViaInstant(queuedOn);

    }
}
