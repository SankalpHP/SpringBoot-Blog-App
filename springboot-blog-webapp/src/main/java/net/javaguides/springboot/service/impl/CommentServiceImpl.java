package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dto.CommentDto;
import net.javaguides.springboot.entity.Comment;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.CommentMapper;
import net.javaguides.springboot.repository.CommentRespository;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.repository.UserRespository;
import net.javaguides.springboot.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements net.javaguides.springboot.service.CommentService {

    private CommentRespository commentRespository;
    private PostRepository postRepository;
    private UserRespository userRespository;

    @Autowired
    public CommentServiceImpl(CommentRespository commentRespository,
                              PostRepository postRepository,
                              UserRespository userRespository) {
        this.commentRespository = commentRespository;
        this.postRepository = postRepository;
        this.userRespository = userRespository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {

        Post post = this.postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.maptoComment(commentDto);
        comment.setPost(post);
        this.commentRespository.save(comment);
    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> comments = this.commentRespository.findAll();
        return comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        this.commentRespository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findCommentsByPost() {
        String email = SecurityUtils.getCurrentuser().getUsername();
        User createdBy = this.userRespository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Comment> comments = this.commentRespository.findCommentsBypost(userId);
        return comments.stream()
                .map((comment) -> CommentMapper.mapToCommentDto(comment))
                .collect(Collectors.toList());
    }
}
