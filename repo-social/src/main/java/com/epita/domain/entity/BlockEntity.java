package com.epita.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @With
public class BlockEntity {
    public String id;
    public String userId;
    public String blockId;
    public LocalDateTime blockAt;
}
