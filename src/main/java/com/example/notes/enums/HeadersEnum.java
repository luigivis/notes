package com.example.notes.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeadersEnum {
    JWT("Authorization");

    private final String value;
}
