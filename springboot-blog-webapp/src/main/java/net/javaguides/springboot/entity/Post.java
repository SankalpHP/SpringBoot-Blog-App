package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// lombok annotation to reduce boilerplate code (Getter,Setter,Constructor)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Hibernate JPA Annotation
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tittle", nullable = false) // nullable = false -> This Column Must be Filled you Can't keep it null
    private String tittle;

    @Column(name = "url")
    private String url;

    @Lob // Content of any blog post is very long so to hold that long data we have to specific content column as a lob
    @Column(name = "content", nullable = false, columnDefinition = "longtext")
    // nullable = false -> This Column Must be Filled you Can't keep it null
    private String content;

    @Column(name = "shortDescription")
    private String shortDescription;

    @CreationTimestamp
    // Auto Generate a time while a creation of any blog post so low level code is Reduce for developer
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @UpdateTimestamp // Auto update a time while a creation of any blog post so low level code is Reduce for developer
    @Column(name = "updateOn")
    private LocalDateTime updateOn;

    // Owner of this relation is post so mapped by post & if the post is deleted then comments on that post also must deleted so CascadeType is Remove
    // One Post can have many comments so that's why we use set and one to many hibernate mapping
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}
