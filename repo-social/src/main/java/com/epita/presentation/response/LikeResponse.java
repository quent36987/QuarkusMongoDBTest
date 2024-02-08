package com.epita.presentation.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @With
public class LikeResponse {
    public String id;
    public String userId;
    public String postId;
    public boolean like;
    public LocalDateTime likeAt;
}
