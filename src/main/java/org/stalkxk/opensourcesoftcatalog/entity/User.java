package org.stalkxk.opensourcesoftcatalog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.parameters.P;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString(exclude = "commentList")
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @ManyToMany(mappedBy = "userList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentList;

    public void addRole(Role role){
        role.getUserList().add(this);
        roles.add(role);
    }
}
