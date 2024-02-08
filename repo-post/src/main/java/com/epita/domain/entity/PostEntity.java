package com.epita.domain.entity;

import io.smallrye.common.constraint.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@With
public class PostEntity {
    public String id;
    public @NotNull String userId;
    public @NotNull LocalDateTime createdAt;
    public String text;
    public String media; // url's image/article
    public String replyPostId;
    public String repostId;
}
