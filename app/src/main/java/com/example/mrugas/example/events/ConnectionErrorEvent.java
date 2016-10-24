package com.example.mrugas.example.events;

/**
 * Created by mruga on 24.10.2016.
 */
public class ConnectionErrorEvent {
    public String getError() {
        return error;
    }

    private String error;

    public ConnectionErrorEvent(String error) {
        this.error = error;
    }

}
