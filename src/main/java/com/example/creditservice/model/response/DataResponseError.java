package com.example.creditservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataResponseError<E> {
    private E error;
}
