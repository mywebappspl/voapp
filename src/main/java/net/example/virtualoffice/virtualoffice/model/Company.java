package net.example.virtualoffice.virtualoffice.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name="companies",
        uniqueConstraints=
        @UniqueConstraint(name="companies_UN", columnNames={"company_name"}))
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message="Company name cannot be empty")
    @Column(unique = true)
    private String company_name;
    @NotBlank(message = "Phone number cannot be empty")
    private String phone;
    @Email(message="Invalid email address")
    @Column(unique = true)
    private String email;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "company_id")
   // @NotNull(message="minimum one member needs to be added")
    private Set<Member> members;
    private boolean active;

}
