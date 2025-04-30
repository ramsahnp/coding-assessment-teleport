package com.teleport.service;

import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.teleport.exception.ListenToChanges;
import com.teleport.model.BlogPost;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ChangeStreamService {

    Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final MongoTemplate mongoTemplate;

    public ChangeStreamService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void startChangeStreamListener() {
        new Thread(this::listenToChanges).start();
    }

    public void listenToChanges() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("blogposts");

        // Start the change stream on the collection
        ChangeStreamIterable<Document> changeStream = collection.watch();

        try (MongoCursor<ChangeStreamDocument<Document>> cursor = changeStream.iterator()) {
            while (cursor.hasNext()) {
                ChangeStreamDocument<Document> change = cursor.next();
                logger.info("Change detected: {}", change);
            }
        } catch (Exception e) {
            throw new ListenToChanges("exception while Change detected");
        }
    }

    // Aggregation query to get recent posts by a specific author
    public List<BlogPost> getRecentPostsByAuthor(String author) {
        startChangeStreamListener();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("author").is(author)),
                Aggregation.sort(Sort.by(Sort.Order.desc("createdAt"))),
                Aggregation.project("title", "content"),
                Aggregation.limit(2)
        );

        AggregationResults<BlogPost> results = mongoTemplate.aggregate(aggregation, BlogPost.class, BlogPost.class);
        return results.getMappedResults();
    }
}
