package com.ticketmaster.exceptions;

/**
 * Created by root on 21/1/16.
 */
public class TicketNotFoundException extends Exception {

    TicketNotFoundException(final String message){
        super(message);
    }
}
