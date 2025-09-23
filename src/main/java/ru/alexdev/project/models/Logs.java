package ru.alexdev.project.models;

import java.time.LocalDateTime;

public class Logs {

    private long id;
    private long idReader;
    private String actionType; // To Do - сменить на Enum
    private LocalDateTime timestamp;

    public Logs() {
    }

    public Logs(long id, long idReader, String actionType, LocalDateTime timestamp) {
        this.id = id;
        this.idReader = idReader;
        this.actionType = actionType;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdReader() {
        return idReader;
    }

    public void setIdReader(long idReader) {
        this.idReader = idReader;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
