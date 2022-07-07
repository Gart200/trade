package com.trade.exception;

public class NoUsersFoundException extends Exception {
    public NoUsersFoundException() {
        super("Пользователей не найдено");
    }
}
