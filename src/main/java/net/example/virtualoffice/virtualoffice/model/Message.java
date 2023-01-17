package net.example.virtualoffice.virtualoffice.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String fromName;
    private String fromEmail;
    @NotBlank
    private String fromPhone;
    @NotBlank
    private String content;
    private int deliveryType;
    @NotNull
    private int companyId;
    private LocalDateTime createdOn;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "message")
    Set<TakenFor> takenForMembers;
}
