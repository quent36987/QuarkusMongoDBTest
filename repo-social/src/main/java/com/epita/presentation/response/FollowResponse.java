package com.epita.presentation.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @With
public class FollowResponse {
    public String id;
    public String userId;
    public String followId;
    public LocalDateTime followedAt;
}
