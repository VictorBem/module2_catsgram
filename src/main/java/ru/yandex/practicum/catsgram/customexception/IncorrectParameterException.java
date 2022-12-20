package ru.yandex.practicum.catsgram.customexception;

public class IncorrectParameterException extends RuntimeException{
    private String param;
    IncorrectParameterException(String message, String param) {
        super(message);
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
