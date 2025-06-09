package com.chat.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class Result<T> {

    private T data;
    private HttpStatus status;
    private String message;

    @Builder
    public Result(T data, HttpStatus status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
}
