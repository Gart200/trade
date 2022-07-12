package com.trade.exception;

public class NotEnoughRights extends Exception{
    public NotEnoughRights() {
        super("У вас недостаточно прав для этой операции");
    }
}
