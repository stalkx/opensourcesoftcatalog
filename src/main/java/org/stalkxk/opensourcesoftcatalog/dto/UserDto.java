package org.stalkxk.opensourcesoftcatalog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    /**
     * Унікальний ідентифікатор користувача.
     */
    private Integer userId;

    /**
     * Логін користувача.
     */
    private String login;
}
