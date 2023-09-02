package com.example.order.Exception;

public class RuleException extends RuntimeException{

    private String errorMsg;

    public RuleException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return this.errorMsg;
    }
    public void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }

    public String toString() {
        return errorMsg;
    }

}
