package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Company;

@Setter
@Getter
public class ReadCompanyDto extends ReadBasicCompanyDTO{

    private String phone;
    private String email;
    private boolean active;
    public ReadCompanyDto(final Company source) {
        super(source);
        this.phone = source.getPhone();
        this.email = source.getEmail();
        this.active = source.isActive();
    }
}
