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
    private int message_id;
    private String fromName;
    private int company_id;
    private String name;
    private String email;
    private String msg_status;
    private LocalDateTime queued_on;
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
    public QueueView(final int id, final int message_id, final String fromName, final int company_id, final String name, final String email, final int msg_status, final Date queued_on) {
        this.id=id;
        this.message_id = message_id;
        this.fromName = fromName;
        this.company_id = company_id;
        this.name = name;
        this.email = email;
        this.msg_status = readStatus(msg_status);
        this.queued_on = voutils.convertToLocalDateTimeViaInstant(queued_on);

    }
}
