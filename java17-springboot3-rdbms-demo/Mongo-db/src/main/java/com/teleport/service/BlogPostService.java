package com.teleport.service;

import com.teleport.model.BlogPost;
import com.teleport.model.Task;
import com.teleport.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BlogPostService {
    private final ChangeStreamService changeStreamService;
    private final BlogPostRepository blogPostRepository;

    @Autowired
    public BlogPostService(ChangeStreamService changeStreamService, BlogPostRepository blogPostRepository){
        this.changeStreamService=changeStreamService;
        this.blogPostRepository=blogPostRepository;
    }

    public BlogPost addBlogPost(BlogPost blogPost) {
        blogPost.setId(UUID.randomUUID().toString().split("-")[0]);
        return blogPostRepository.save(blogPost);
    }

    public List<BlogPost> getRecentPostByAuthor(String author) {
        return changeStreamService.getRecentPostsByAuthor(author);
    }

    public List<BlogPost> findAllBlogs() {
        return blogPostRepository.findAll();
    }

}
