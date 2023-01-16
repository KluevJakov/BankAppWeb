package com.kluevja.bankappweb.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MessageDTO {
    private String message;
    private boolean success;
}
