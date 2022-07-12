package com.trade.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super(String.format("Пользователь с таким email или номером телефона уже существует"));
    }
}
