package com.studentmanagement.common.response;

public class ErrorResponse {
    private String message;
    private String httpCode;
    private int httpStatus;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ErrorResponse(String message, String httpCode, int httpStatus) {
        this.message = message;
        this.httpCode = httpCode;
        this.httpStatus = httpStatus;
    }

}
