package net.javaguides.springboot.controller;

import jakarta.validation.Valid;
import jdk.dynalink.beans.StaticClass;
import net.javaguides.springboot.dto.CommentDto;
import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.service.CommentService;
import net.javaguides.springboot.service.PostService;
import net.javaguides.springboot.util.ROLE;
import net.javaguides.springboot.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostService postService;
    private CommentService commentService;

    @Autowired
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }


    // pre-processor method to remove white spaces
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // create handler method, GET request and return model and view
    @GetMapping("/admin/posts")
    public String post(Model model) {
        String role = SecurityUtils.getRole();
        List<PostDto> posts = null;

        if (ROLE.ROLE_ADMIN.name().equals(role)) {
            posts = this.postService.findAllPosts();
        } else {
            posts = this.postService.findPostsByUser();
        }

        model.addAttribute("posts", posts);
        return "/admin/posts";
    }

    // handler method to handle new post request
    @GetMapping("/admin/posts/newpost")
    public String newPostForm(@ModelAttribute("post") PostDto postDto, Model model) {

        model.addAttribute("post", postDto);

        return "/admin/create_post";
    }

    // handler method to handle form submit request
    @PostMapping("/admin/posts")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postDto);
            return "/admin/create_post";
        }

        postDto.setUrl(getUrl(postDto.getTittle()));
        this.postService.createPost(postDto);
        return "redirect:/admin/posts";
    }

    // handler method to handle edit post request
    @GetMapping("/admin/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId,
                               Model model) {

        PostDto postDto = this.postService.findPostbyId(postId);
        model.addAttribute("post", postDto);
        return "admin/edit_post";
    }

    // handler method to handle edit post form submit request
    @PostMapping("/admin/posts/{postId}")
    public String updatePost(@Valid @ModelAttribute("post") PostDto postDto, BindingResult bindingResult,
                             @PathVariable("postId") Long postId,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postDto);
            return "admin/edit_post";
        }
        postDto.setId(postId);
        this.postService.updatePost(postDto);
        return "redirect:/admin/posts";
    }

    // handler method to handle delete post request
    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        this.postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    // handler method to handle list comments request
    @GetMapping("/admin/posts/comments")
    public String postComments(Model model) {
        String role = SecurityUtils.getRole();
        List<CommentDto> comments = null;

        if (ROLE.ROLE_ADMIN.name().equals(role)) {
            comments = this.commentService.findAllComments();
        } else {
            comments = this.commentService.findCommentsByPost();
        }

        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    // handler method to handle delete comment request
    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        this.commentService.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }

    // handler method to handle view post request
    @GetMapping("/admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl,
                           Model model) {
        PostDto postDto = this.postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "admin/view_post";
    }

    // handler method to handle search blog posts request
    // localhost:8080/admin/posts/search?query=java
    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam(value = "query") String query,
                              Model model) {

        List<PostDto> posts = this.postService.searchPosts(query);
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    private static String getUrl(String postTitle) {

        /*
           OOPS Concepts Explained in JAVA
           CONVERT INTO :-
           oops-concepts-explained-in-java
         */

        // To Convert Upper Case to Lowercase
        String title = postTitle.trim().toLowerCase();

        // To Replace Spaces With -
        String url = title.replaceAll("\\s+", "-");

        // TO Replace Symbols With -
        url = url.replaceAll("[^A-Za-z0-9]", "-");

        return url;
    }
}
