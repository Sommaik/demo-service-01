package com.example.demo.dto;

public class UpdateResultDTO<T> {
    boolean success;
    int effectRow;
    T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getEffectRow() {
        return effectRow;
    }

    public void setEffectRow(int effectRow) {
        this.effectRow = effectRow;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
