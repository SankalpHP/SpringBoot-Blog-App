package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // query method or a finder method
    Optional<Post> findByUrl(String url);

    // JPQL Query is Used
    @Query("SELECT p from Post p WHERE " +
            "p.tittle LIKE CONCAT('%', :query, '%') OR " +
            "p.shortDescription LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);

    // Normal SQL Query is Used i.e nativeQuery
    @Query(value = "select * from posts p where p.created_by = :userId", nativeQuery = true)
    List<Post> findPostsByUser(Long userId);
}
