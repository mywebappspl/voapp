package net.example.virtualoffice.virtualoffice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="members")

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @NotBlank(message="Employee name cannot be blank")
    private String name;
    @NotBlank(message="phone number cannot be blank")
    private String phone;
    @NotBlank(message="Email address cannot be blank")
    @Email(message="Invalid email address")
    private String email;
    private boolean active;
    @NotNull(message = "Member needs company id")
    private int companyId;
    public Member(String name, String phone, String email,int companyId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.companyId = companyId;
        this.active = true;
    }
}