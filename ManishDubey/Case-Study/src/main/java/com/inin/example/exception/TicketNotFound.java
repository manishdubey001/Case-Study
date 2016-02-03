package com.inin.example.exception;

/**
 * Created by root on 3/2/16.
 */
public class TicketNotFound extends RuntimeException{
    public TicketNotFound(String message){
        super(message);
    }
}
