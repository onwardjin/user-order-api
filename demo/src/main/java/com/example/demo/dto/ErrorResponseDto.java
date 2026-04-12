package com.example.demo.dto;

public class ErrorResponseDto {
    private int status;
    private String message;

    public ErrorResponseDto(int status, String message){
        this.status = status;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
