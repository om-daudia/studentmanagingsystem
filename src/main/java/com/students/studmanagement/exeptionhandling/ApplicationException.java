package com.students.studmanagement.exeptionhandling;

import com.students.studmanagement.response.ErrorResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{
    private HttpStatus statusCode;

    public ApplicationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApplicationException(String responseBody) {
        super(extractMessage(responseBody));
        this.statusCode = extractStatus(responseBody);

    }

    private static String extractMessage(String responseBody) {
        try {
            JSONObject json = new JSONObject(responseBody);
            return json.optString("msg", "BAD_REQUEST");
        } catch (Exception e) {
            return "Failed to parse error message";
        }
    }

    private static HttpStatus extractStatus(String responseBody) {
        try {
            JSONObject json = new JSONObject(responseBody);
            int code = json.optInt("httpStatus", 401);
            return HttpStatus.valueOf(code);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }


}

