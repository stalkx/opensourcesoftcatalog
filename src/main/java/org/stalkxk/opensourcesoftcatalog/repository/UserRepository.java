package org.stalkxk.opensourcesoftcatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stalkxk.opensourcesoftcatalog.entity.Program;
import org.stalkxk.opensourcesoftcatalog.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
    Page<User> findAllByLoginContains(String login, Pageable pageable);
}
