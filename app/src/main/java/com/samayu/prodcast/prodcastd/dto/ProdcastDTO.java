package com.samayu.prodcast.prodcastd.dto;

/**
 * Created by sarathan732 on 8/10/2017.
 */

public class ProdcastDTO {

    public ProdcastDTO(){

    }
    private boolean error;
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    private String errorMessage;
    private boolean validationFailed;
    private String validationErrorMessage;

    public boolean isValidationFailed() {
        return validationFailed;
    }

    public void setValidationFailed(boolean validationFailed) {
        this.validationFailed = validationFailed;
    }

    public String getValidationErrorMessage() {
        return validationErrorMessage;
    }

    public void setValidationErrorMessage(String validationErrorMessage) {
        this.validationErrorMessage = validationErrorMessage;
    }
}

