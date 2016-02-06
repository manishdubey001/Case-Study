package com.customexceptions;

/**
 * Created by root on 15/1/16.
 */
public class UserInputException extends Exception {
    private String message = null;


    /*public UserInputException(){
        super();
    }*/


    public UserInputException(String message){
        super(message);
        this.message = message;
    }


    /*public UserInputException(Throwable cause){
        super(cause);
    }*/

    @Override
    public String toString(){
        return message;
    }


    @Override
    public String getMessage(){
        return message;
    }
}
