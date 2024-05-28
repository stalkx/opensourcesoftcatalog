package org.stalkxk.opensourcesoftcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Клас для представлення JWT-відповіді.
 *
 * @author Krupanych
 */
@Data
@AllArgsConstructor
@Builder
public class JwtResponse {

    /**
     * JWT-токен.
     */
    private String token;

    /**
     * Список ролей користувача.
     */
    private List<String> rolesList;
}
