package org.stalkxk.opensourcesoftcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stalkxk.opensourcesoftcatalog.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
}
