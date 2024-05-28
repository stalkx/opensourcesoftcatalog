package org.stalkxk.opensourcesoftcatalog.dto;

import lombok.Builder;
import lombok.Data;
import org.stalkxk.opensourcesoftcatalog.entity.Role;

@Data
@Builder
public class RegistrationUserDto {
    /**
     * Логін користувача.
     */
    private String login;
    private Role role;
    /**
     * Пароль користувача.
     */
    private String password;
}
