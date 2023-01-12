package net.example.virtualoffice.virtualoffice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private LocalDateTime createdOn;
    @NotBlank
    private String message;
    @NotBlank
    private int companyId;
    @NotBlank
    private int agentId;

}
