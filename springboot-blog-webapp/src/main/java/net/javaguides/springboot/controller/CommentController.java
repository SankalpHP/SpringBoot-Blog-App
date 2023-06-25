package net.javaguides.springboot.controller;

import jakarta.validation.Valid;
import net.javaguides.springboot.dto.CommentDto;
import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.service.CommentService;
import net.javaguides.springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private CommentService commentService;
    private PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    // pre-processor method to remove white spaces
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // handler method to create form submit request
    @PostMapping("/{postUrl}/comments")
    public String createComment(@PathVariable("postUrl") String postUrl,
                                @Valid @ModelAttribute("comment") CommentDto commentDto,
                                BindingResult result,
                                Model model) {

        PostDto postDto = this.postService.findPostByUrl(postUrl);
        if (result.hasErrors()) {
            model.addAttribute("post", postDto);
            model.addAttribute("comment", commentDto);
            return "blog/blog_post";
        }

        this.commentService.createComment(postUrl, commentDto);
        return "redirect:/post/" + postUrl;
    }
}
