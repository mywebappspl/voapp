package net.example.virtualoffice.virtualoffice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.projection.QueueView;

import javax.persistence.*;
import java.time.LocalDateTime;


@NamedNativeQueries({
        @NamedNativeQuery(name = "TakenFor.findAllFromQueueDTO",
                query = "SELECT t.id as id, m.id as message_id, m.from_name as fromName, m.company_id as company_id, e.name as name, e.email as email, t.msg_status as msg_status, t.queued_on as queued_on FROM taken_for t INNER JOIN messages m ON t.message_id=m.id INNER JOIN members e ON t.member_id=e.id ORDER BY t.message_id",
                resultSetMapping = "Mapping.QueueView"
        ),
        @NamedNativeQuery(name = "TakenFor.findAllFromQueueDTO.count",
                query = "select count(*) as cnt FROM taken_for",
                resultSetMapping = "SqlResultSetMapping.count"
        ),
        @NamedNativeQuery(name = "TakenFor.findAllFromQueueDTOByDate",
                query = "SELECT t.id as id, m.id as message_id, m.from_name as fromName, m.company_id as company_id, e.name as name, e.email as email, t.msg_status as msg_status, t.queued_on as queued_on FROM taken_for t INNER JOIN messages m ON t.message_id=m.id INNER JOIN members e ON t.member_id=e.id WHERE queued_on BETWEEN :startDate AND :endDate ORDER BY t.message_id",
                resultSetMapping = "Mapping.QueueView"
        ),
        @NamedNativeQuery(name = "TakenFor.findAllFromQueueDTOByDate.count",
                query = "select count(*) as cnt FROM taken_for WHERE queued_on BETWEEN :startDate AND :endDate",
                resultSetMapping = "SqlResultSetMapping.count"
        )
})
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "Mapping.QueueView",
                classes = @ConstructorResult(targetClass = QueueView.class,
                        columns = {
                                @ColumnResult(name = "id"),
                                @ColumnResult(name = "message_id"),
                                @ColumnResult(name = "fromName"),
                                @ColumnResult(name = "company_id"),
                                @ColumnResult(name = "name"),
                                @ColumnResult(name = "email"),
                                @ColumnResult(name = "msg_status"),
                                @ColumnResult(name = "queued_on"),
                        }
                )
        ),
        @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
})
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "taken_for")
public class TakenFor {
    public TakenFor(final Message message, final int memberId) {
        this.message = message;
        this.memberId = memberId;
        this.queuedOn = LocalDateTime.now();
        this.msgStatus = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int memberId;
    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;
    private int msgStatus;
    private LocalDateTime queuedOn;
    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;
}
