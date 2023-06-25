package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.PostMapper;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.repository.UserRespository;
import net.javaguides.springboot.service.PostService;
import net.javaguides.springboot.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRespository userRespository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRespository userRespository) {
        this.postRepository = postRepository;
        this.userRespository = userRespository;
    }

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts = this.postRepository.findAll();
        return posts.stream().map(PostMapper::mapToPostDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostsByUser() {
        String email = SecurityUtils.getCurrentuser().getUsername();
        User createdBy = this.userRespository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Post> posts = this.postRepository.findPostsByUser(userId);
        return posts.stream()
                .map((post) -> PostMapper.mapToPostDto(post))
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        String email = SecurityUtils.getCurrentuser().getUsername();
        User user = this.userRespository.findByEmail(email);
        Post post = PostMapper.mapToPost(postDto);
        post.setCreatedBy(user);
        this.postRepository.save(post);
    }

    @Override
    public PostDto findPostbyId(Long postId) {
        Post post = this.postRepository.findById(postId).get();
        return PostMapper.mapToPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        String email = SecurityUtils.getCurrentuser().getUsername();
        User user = this.userRespository.findByEmail(email);
        Post post = PostMapper.mapToPost(postDto);
        post.setCreatedBy(user);
        this.postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        this.postRepository.deleteById(postId);
    }

    @Override
    public PostDto findPostByUrl(String postUrl) {
        Post post = this.postRepository.findByUrl(postUrl).get();
        PostDto postDto = PostMapper.mapToPostDto(post);
        return postDto;
    }

    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = this.postRepository.searchPosts(query);
        return posts.stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }
}
