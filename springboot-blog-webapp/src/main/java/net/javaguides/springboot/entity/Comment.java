package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Lob
    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @Column(name = "createdOn")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updatedOn")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @ManyToOne // One Post Have Comments
    @JoinColumn(name = "post_id",nullable = false) // Foreign Key Column
    private Post post;
}
