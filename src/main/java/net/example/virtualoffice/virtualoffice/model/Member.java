package net.example.virtualoffice.virtualoffice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
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
    private int company_id;
    public Member(){}
    public Member(String name, String phone, String email,int company_id) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company_id = company_id;
        this.active = true;
    }
}