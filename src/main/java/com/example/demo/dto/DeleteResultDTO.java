package com.example.demo.dto;

public class DeleteResultDTO {
    int effectRow;
    boolean success;
    String errorMessage;

    public int getEffectRow() {
        return effectRow;
    }

    public void setEffectRow(int effectRow) {
        this.effectRow = effectRow;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
