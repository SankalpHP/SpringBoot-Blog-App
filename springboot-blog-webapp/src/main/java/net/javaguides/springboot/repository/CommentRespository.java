package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRespository extends JpaRepository<Comment, Long> {

    // Normal SQL Query i.e nativeQuery
    @Query(value = "select c.* from comments c inner join posts p\n" +
            "where c.post_id = p.id and p.created_by = :userId", nativeQuery = true)
    List<Comment> findCommentsBypost(Long userId);
}
