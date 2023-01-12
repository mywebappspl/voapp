package net.example.virtualoffice.virtualoffice.model.projection;

import net.example.virtualoffice.virtualoffice.model.Member;

public class ReadMemberDto {
    private int id;
    private String name;
    private String phone;
    private String email;
    private boolean active;
    private int companyId;

    public ReadMemberDto(final Member source) {
        this.id=source.getId();
        this.name=source.getName();
        this.email=source.getEmail();
        this.phone= source.getPhone();
        this.active= source.isActive();

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }
}
