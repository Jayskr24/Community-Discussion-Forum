package com.community.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "threads")
public class Thread {
    @Id
    private String id;
    private String title;
    private String content;
    private String category;
    private String authorId;
    private String authorName;
    private String mediaUrl = "";// Images / Videos local/remote references
    private String status = "ACTIVE";

    private List<String> upvotedUserIds = new ArrayList<>();
    private List<String> downvotedUserIds = new ArrayList<>();
    private List<Reply> replies = new ArrayList<>();

    // Helper Dynamic Getters for Frontend Calculators
    public int getUpvotes() { return upvotedUserIds.size() - downvotedUserIds.size(); }

    // Boilerplate Getters/Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
    public List<String> getUpvotedUserIds() { return upvotedUserIds; }
    public void setUpvotedUserIds(List<String> upvotedUserIds) { this.upvotedUserIds = upvotedUserIds; }
    public List<String> getDownvotedUserIds() { return downvotedUserIds; }
    public void setDownvotedUserIds(List<String> downvotedUserIds) { this.downvotedUserIds = downvotedUserIds; }
    public List<Reply> getReplies() { return replies; }
    public void setReplies(List<Reply> replies) { this.replies = replies; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}