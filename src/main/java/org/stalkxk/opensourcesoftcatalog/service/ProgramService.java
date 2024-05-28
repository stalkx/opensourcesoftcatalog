package org.stalkxk.opensourcesoftcatalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stalkxk.opensourcesoftcatalog.entity.Category;
import org.stalkxk.opensourcesoftcatalog.entity.Program;
import org.stalkxk.opensourcesoftcatalog.repository.ProgramRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramService {
    private final ProgramRepository programRepository;
    private final CategoryService categoryService;

    public Program saveProgram(Program program){
        return programRepository.save(program);
    }

    @Transactional(readOnly = true)
    public Optional<Program> findById(Integer programId){
        return programRepository.findById(programId);
    }

    public Page<Program> findAllByCategory(Integer categoryId, Pageable pageable){
        Category category = categoryService.findById(categoryId).orElseThrow();
        return programRepository.findAllByCategory(category, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Program> findAllProgram(Pageable pageable){
        return programRepository.findAll(pageable);
    }

    /// TO DOOOO ///
    public Program updateProgram(Program updatedProgram){
        Program program = findById(updatedProgram.getProgramId()).orElseThrow();
        program.setProgramName(updatedProgram.getProgramName());
        program.setProgramDescription(updatedProgram.getProgramDescription());
        program.setProgramDeveloper(updatedProgram.getProgramDeveloper());
        program.setProgramId(updatedProgram.getProgramId());
        program.setProgramVersion(updatedProgram.getProgramVersion());
        program.setProgramDownloadUrl(updatedProgram.getProgramDownloadUrl());
        program.setProgramGitHubUrl(updatedProgram.getProgramGitHubUrl());
        program.setAddedAt(updatedProgram.getAddedAt());
        program.setCategory(updatedProgram.getCategory());
        return programRepository.save(program);
    }

    public void removeProgram(Program program){
        programRepository.delete(program);
    }
}
