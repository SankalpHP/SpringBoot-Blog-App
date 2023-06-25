package net.javaguides.springboot.mapper;


import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.entity.Post;

import java.util.stream.Collectors;

public class PostMapper {

    // map Post entity to PostDto
    public static PostDto mapToPostDto(Post post) {

        return PostDto.builder()
                .id(post.getId())
                .tittle(post.getTittle())
                .url(post.getUrl())
                .content(post.getContent())
                .shortDescription(post.getShortDescription())
                .createdOn(post.getCreatedOn())
                .updateOn(post.getUpdateOn())
                .comments(post.getComments().stream()
                        .map((comment) -> CommentMapper.mapToCommentDto(comment))
                        .collect(Collectors.toSet()))
                .build();
    }

    // map PostDto to Post entity
    public static Post mapToPost(PostDto postDto) {

        return Post.builder()
                .id(postDto.getId())
                .tittle(postDto.getTittle())
                .url(postDto.getUrl())
                .content(postDto.getContent())
                .shortDescription(postDto.getShortDescription())
                .createdOn(postDto.getCreatedOn())
                .updateOn(postDto.getUpdateOn())
                .build();
    }
}
// NOTE
/* Mapper class to map entity layer to Dto layer , so it will convert entity to dto
   and dto to entity

   .builder method -> is builder user design pattern we use to design using lombok annotation
   in JPA Entity and Dto Class

   .build method ->  is to build a object
 */
