package com.teleport.repository;

import com.teleport.model.BlogPost;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface BlogPostRepository extends MongoRepository<BlogPost,String> {
}
