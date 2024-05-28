package org.stalkxk.opensourcesoftcatalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stalkxk.opensourcesoftcatalog.entity.Role;
import org.stalkxk.opensourcesoftcatalog.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервіс для роботи з ролями користувачів.
 *
 * @author [Krupanych]
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Знаходить роль за її назвою.
     *
     * @param roleName назва ролі
     * @return Optional об'єкта Role, що містить знайдену роль, або Optional.empty(), якщо роль не знайдено.
     */
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    /**
     * Знаходить роль за її ID.
     *
     * @param roleId ID ролі
     * @return об'єкт Role
     * @throws IllegalArgumentException якщо роль не знайдено
     */
    @Transactional(readOnly = true)
    public Optional<Role> findById(Integer roleId) {
        return roleRepository.findById(roleId);
    }

    /**
     * Отримує список усіх ролей.
     *
     * @return список об'єктів Role
     */
    @Transactional(readOnly = true)
    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    /**
     * Зберігає роль.
     *
     * @param role роль, яку потрібно зберегти
     * @return збережена роль
     */
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Видаляє роль.
     *
     * @param role роль, яку потрібно видалити
     */
    public void removeRole(Role role) {
        roleRepository.delete(role);
    }

    /**
     * Оновлює роль.
     *
     * @param updatedRole оновлена роль
     * @throws IllegalArgumentException якщо роль не знайдено
     */
    public Role updateRole(Role updatedRole) {
        Role role = findById(updatedRole.getRoleId()).orElseThrow();
        role.setRoleId(updatedRole.getRoleId());
        role.setRoleName(updatedRole.getRoleName());
        role.setUserList(updatedRole.getUserList());
        return roleRepository.save(role);
    }
}
