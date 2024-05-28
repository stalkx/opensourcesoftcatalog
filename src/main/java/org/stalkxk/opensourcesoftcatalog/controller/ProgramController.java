package org.stalkxk.opensourcesoftcatalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.stalkxk.opensourcesoftcatalog.entity.Comment;
import org.stalkxk.opensourcesoftcatalog.entity.Program;
import org.stalkxk.opensourcesoftcatalog.exception.AppException;
import org.stalkxk.opensourcesoftcatalog.service.ProgramService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/program")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class ProgramController {
    private final ProgramService programService;

    @GetMapping
    public ResponseEntity<Page<Program>> findAllProgram(Pageable pageable){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(programService.findAllProgram(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        if(programService.findById(id).isPresent()){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(programService.findById(id).get());
        }else {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new AppException(HttpStatus.NOT_FOUND.value(), "Дані відстутні"));
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Page<Program>> findByProgramId(@PathVariable Integer id, Pageable pageable){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(programService.findAllByCategory(id, pageable));
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> saveProgram(@RequestBody Program program){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(programService.saveProgram(program));
    }

    @PostMapping("/update")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateProgram(@RequestBody Program updatedProgram){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(programService.updateProgram(updatedProgram));
    }

    @PostMapping("/remove/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> removeProgram(@PathVariable Integer id){
        Program program = programService.findById(id).orElseThrow();
        programService.removeProgram(program);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("message", "Дані успішно видалені"));
    }
}
