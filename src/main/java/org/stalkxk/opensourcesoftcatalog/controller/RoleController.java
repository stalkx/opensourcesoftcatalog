package org.stalkxk.opensourcesoftcatalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.stalkxk.opensourcesoftcatalog.entity.Role;
import org.stalkxk.opensourcesoftcatalog.exception.AppException;
import org.stalkxk.opensourcesoftcatalog.service.RoleService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Page<Role>> findAllRoles(Pageable pageable){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(roleService.findAllRoles(pageable));
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> findById(@PathVariable Integer id){
        if(roleService.findById(id).isPresent()){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(roleService.findById(id).get());
        }else {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new AppException(HttpStatus.NOT_FOUND.value(), "Дані відстутні"));
        }
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> saveRole(@RequestBody Role role){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(roleService.saveRole(role));
    }

    @PostMapping("/update")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateRole(@RequestBody Role updatedRole){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(roleService.updateRole(updatedRole));
    }

    @PostMapping("/remove")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> removeRole(@RequestBody Role role){
        roleService.removeRole(role);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("message", "Дані успішно видалені"));
    }
}
