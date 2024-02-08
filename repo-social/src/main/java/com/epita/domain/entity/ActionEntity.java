package com.epita.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@With
public class ActionEntity {
    public String userId;
    public String postId;
    public String action; // FIXME: convert to enum ?
    public LocalDateTime actionAt = LocalDateTime.now();
}
