package net.javaguides.springboot.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.springboot.entity.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

// Lombok Annotation
@Data // auto create getter,setter,constructor,toString,Override Methods
@Builder // auto build a user Builder pattern
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty(message = "Post title should not be empty")
    private String tittle;
    private String url;
    @NotEmpty(message = "Post content should not be empty")
    private String content;
    @NotEmpty(message = "Post short description should not be empty")
    private String shortDescription;
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
    private Set<CommentDto> comments;
}
// NOTE
/* DTO class basically a model class to communicate between view and Controller
   its is use to bind a form data into to controller
   offCourse we can use entity layer directly to bind a form data as previously we use
   but creating a Dto is kind of a Good practice
 */