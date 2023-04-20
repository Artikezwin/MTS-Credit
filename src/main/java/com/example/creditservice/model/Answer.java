package com.example.creditservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer<E> {
    private E data;
}
