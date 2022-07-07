package com.trade.exception;

public class NoAdFoundException extends Exception{
    public NoAdFoundException(Long id) {
        super(String.format("Объявления с id %d не найдено", id));
    }
}
