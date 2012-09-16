package org.metricminer.model;

public enum QueryResultStatus {
    FAILED, SUCESS;

    private String message;
    
    public String getMessage() {
        return message;
    }
    
    public QueryResultStatus withMessage(String message) {
        this.message = message;
        return this;
    }
}
