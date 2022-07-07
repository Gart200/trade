package com.trade.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String email) {
        super(String.format("Пользователь с email %s уже существует", email));
    }
}
