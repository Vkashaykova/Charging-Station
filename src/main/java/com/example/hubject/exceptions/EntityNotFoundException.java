package com.example.hubject.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String type, String value) {
        super((String.format("%s with %s not found.", type, value)));
    }


    public EntityNotFoundException(String type, String attribute, String value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }

}