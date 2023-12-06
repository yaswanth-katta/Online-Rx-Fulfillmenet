package com.cts.ordermodule.exception;


public class StockUnAvailableException extends Exception{
    public StockUnAvailableException(String message){
        super(message);
    }
}
