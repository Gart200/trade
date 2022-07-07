package com.trade.exception;

public class NoUserFoundException extends Exception {
    public NoUserFoundException(Long id) {
        super(String.format("Пользователя с id %d не найдено", id));
    }
}
