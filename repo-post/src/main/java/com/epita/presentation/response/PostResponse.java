package com.epita.presentation.response;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
public class PostResponse {

    public String id;
    public String userId;
    public LocalDateTime createdAt;
    public String text;
    public String media; // url's image/article
    public String replyPostId;
    public String repostId;

}
