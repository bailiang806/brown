package com.happydriving.rockets.server.common.json;

/**
 * Common reture response json object for callback.
 * @author mazhiqiang
 */
public class ResponseJsonObject {

    /**
     * Determine whether request is ok/error
     */
    private boolean result;

    /**
     * Redirect url if need to jump
     */
    private String redirectUrl;

    /**
     * If result=false, given the error message; If result=true, it will be null
     */
    private String message;

    /**
     * To some insert operation, the insert new id will be return in this field.
     */
    private int id = 1;

    /**
     * Used for complex return object, wrapper this in final json
     */
    private Object returnObject;

    public ResponseJsonObject() {

    }

    public ResponseJsonObject(boolean result) {
        this.result = result;
    }


    public ResponseJsonObject(boolean result, Object returnObject) {
        this.result = result;
        this.returnObject = returnObject;
    }

    public ResponseJsonObject(boolean result, String message){
        this.result = result;
        this.message = message;
    }

    public ResponseJsonObject(boolean result, String message, int id) {
        this.result = result;
        this.message = message;
        this.id = id;
    }

    public ResponseJsonObject(boolean result, String message, int id, Object returnObject) {
        this.result = result;
        this.message = message;
        this.id = id;
        this.returnObject = returnObject;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
