package org.stalkxk.opensourcesoftcatalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.stalkxk.opensourcesoftcatalog.entity.Category;
import org.stalkxk.opensourcesoftcatalog.exception.AppException;
import org.stalkxk.opensourcesoftcatalog.service.CategoryService;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Page<Category> getAllCategory(Pageable pageable){
        return categoryService.findAllCategory(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        if(categoryService.findById(id).isPresent()){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(categoryService.findById(id).get());
        }else {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new AppException(HttpStatus.NOT_FOUND.value(), "Дані відстутні"));
        }
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(categoryService.saveCategory(category));
    }

    @PostMapping("/update")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateCategory(@RequestBody Category updatedCategory){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(categoryService.updateCategory(updatedCategory));
    }

    @PostMapping("/remove")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> removeCategory(@RequestBody Category category){
        categoryService.removeCategory(category);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("message", "Дані успішно видалені"));
    }
}
