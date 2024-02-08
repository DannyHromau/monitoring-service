package com.dannyhromau.monitoring.meter.api;

public class ResponseEntity<T> {
    private T body;
    private String systemMessage;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public static <T> ResponseEntity<T> of(T t, String systemMessage) {
        ResponseEntity<T> re = new ResponseEntity<>();
        re.setBody(t);
        re.setSystemMessage(systemMessage);
        return re;
    }
}
