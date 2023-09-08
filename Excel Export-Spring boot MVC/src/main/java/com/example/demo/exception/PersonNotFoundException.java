package com.example.demo.exception;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(){
        super();
    }
    public PersonNotFoundException(String message){
        super(message);
    }
}
