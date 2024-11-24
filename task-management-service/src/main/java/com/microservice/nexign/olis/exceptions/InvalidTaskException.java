package com.microservice.nexign.olis.exceptions;

public class InvalidTaskException extends RuntimeException{
    public InvalidTaskException(String message) {
        super(message);
    }
}
