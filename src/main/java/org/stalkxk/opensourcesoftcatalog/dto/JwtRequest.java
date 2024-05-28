package org.stalkxk.opensourcesoftcatalog.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Клас `JwtRequest` використовується для створення запиту на отримання JSON Web Token (JWT).
 *
 * @Data - Ця анотація автоматично генерує методи `equals()`, `hashCode()`, `toString()`, `getter` та `setter`.
 * @Builder - Ця анотація генерує будівельний шаблон, який дозволяє створювати екземпляри класу `JwtRequest` з кращою читабельністю.
 */
@Data
@Builder
public class JwtRequest {

    /**
     * Логін користувача.
     */
    private String login;

    /**
     * Пароль користувача.
     */
    private String password;
}
