package org.stalkxk.opensourcesoftcatalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.stalkxk.opensourcesoftcatalog.entity.Comment;
import org.stalkxk.opensourcesoftcatalog.exception.AppException;
import org.stalkxk.opensourcesoftcatalog.service.CommentService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Page<Comment> getAllComment(Pageable pageable){
        return commentService.findAllComment(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        if(commentService.findById(id).isPresent()){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commentService.findById(id).get());
        }else {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new AppException(HttpStatus.NOT_FOUND.value(), "Дані відстутні"));
        }
    }

    @GetMapping("/average/{programId}")
    public ResponseEntity<?> getAverageRating(@PathVariable Integer programId, Pageable pageable) {
        Double averageRating = commentService.calculateAverageRating(programId, pageable);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("averageRating", averageRating));
    }

    @GetMapping("/program/{id}")
    public ResponseEntity<Page<Comment>> findByProgramId(@PathVariable Integer id, Pageable pageable){
//        Pageable sortedByDateDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("addedAt").descending());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commentService.findAllByProgram(id, pageable));
    }

    @PostMapping("/save")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> saveComment(@RequestBody Comment comment){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commentService.saveComment(comment));
    }

    @PostMapping("/update")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> updateComment(@RequestBody Comment updatedComment){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commentService.updateComment(updatedComment));
    }

    @PostMapping("/remove")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> removeComment(@RequestBody Comment comment){
        commentService.removeComment(comment);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("message", "Дані успішно видалені"));
    }
}
