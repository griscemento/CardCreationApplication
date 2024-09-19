package com.ejercicio2.dto;

import com.ejercicio2.model.Card;
import com.ejercicio2.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
    private User user;
    private Card card;
}
