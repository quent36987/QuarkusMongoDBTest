package com.epita.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CreateReplyRequestBody {
    public String text;
    public String media;
}
