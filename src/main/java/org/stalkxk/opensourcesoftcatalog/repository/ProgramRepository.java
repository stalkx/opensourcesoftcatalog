package org.stalkxk.opensourcesoftcatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stalkxk.opensourcesoftcatalog.entity.Category;
import org.stalkxk.opensourcesoftcatalog.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
    Page<Program> findAllByCategory(Category category, Pageable pageable);
    Page<Program> findAllByProgramNameContains(String programName, Pageable pageable);
}
