package com.epita.presentation.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @With
public class BlockResponse {
    public String id;
    public String userId;
    public String blockId;
    public LocalDateTime blockAt;
}
