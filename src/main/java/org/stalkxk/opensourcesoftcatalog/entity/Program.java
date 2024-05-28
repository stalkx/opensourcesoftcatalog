package org.stalkxk.opensourcesoftcatalog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "program")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Integer programId;
    @Column(name = "program_name")
    private String programName;
    @Column(name = "program_description")
    private String programDescription;
    @Column(name = "program_icon_url")
    private String programIconUrl;
    @Column(name = "program_version")
    private String programVersion;
    @Column(name = "program_developer")
    private String programDeveloper;
    @Column(name = "program_system_support")
    private String programSystemSupport;
    @Column(name = "program_download_url")
    private String programDownloadUrl;
    @Column(name = "program_github_url")
    private String programGitHubUrl;
    @CreationTimestamp
    @Column(name = "added_at")
    private Timestamp addedAt;
    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private List<Comment> commentList;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}