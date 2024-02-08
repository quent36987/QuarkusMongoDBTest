package com.epita.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @With
public class LikeEntity {
    public String id;
    public String userId;
    public String postId;
    public boolean like;
    public LocalDateTime likeAt;
}
