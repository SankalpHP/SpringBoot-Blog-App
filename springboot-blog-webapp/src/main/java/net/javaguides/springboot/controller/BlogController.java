package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.CommentDto;
import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {

    private PostService postService;

    @Autowired
    public BlogController(PostService postService) {
        this.postService = postService;
    }

    // handler method to handle http://localhost:8080/
    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        List<PostDto> postResponse = this.postService.findAllPosts();
        model.addAttribute("postsResponse", postResponse);
        return "blog/view_posts";
    }

    // handler method to handle view post request
    @GetMapping("/post/{postUrl}")
    private String showPost(@ModelAttribute("comment") CommentDto commentDto, @PathVariable("postUrl") String postUrl,
                            Model model) {
        PostDto postDto = this.postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);

        model.addAttribute("comment", commentDto);

        return "blog/blog_post";
    }

    // handler method to handle blog post search request
    // http://localhost:8080/page/search?query=java
    @GetMapping("/page/search")
    public String searchPosts(@RequestParam(value = "query") String query,
                              Model model) {
        List<PostDto> postsResponse = this.postService.searchPosts(query);
        model.addAttribute("postsResponse", postsResponse);
        return "blog/view_posts";
    }
}
