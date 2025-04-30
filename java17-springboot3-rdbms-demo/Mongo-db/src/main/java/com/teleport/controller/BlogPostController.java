package com.teleport.controller;


import com.teleport.model.BlogPost;
import com.teleport.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogPostController {
    private final BlogPostService blogPostService;

    @Autowired
    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost createBlogPost(@RequestBody BlogPost blogPost) {
        return blogPostService.addBlogPost(blogPost);
    }

    @GetMapping("/{author}")
    public List<BlogPost> getLatestBlogByAuthor(@PathVariable String author) {
        return blogPostService.getRecentPostByAuthor(author);
    }

    @GetMapping("/")
    public List<BlogPost> getAllBloga() {
        return blogPostService.findAllBlogs();
    }
}
