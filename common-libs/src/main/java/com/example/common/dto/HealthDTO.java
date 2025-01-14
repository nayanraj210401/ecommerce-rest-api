package com.example.common.dto;

public class HealthDTO {
    private int port;
    private boolean db;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isDb() {
        return db;
    }

    public void setDb(boolean db) {
        this.db = db;
    }
}
