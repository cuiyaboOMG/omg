package com.omg.util.excel;

import java.util.List;

/**
* @Author:         cyb
* @CreateDate:     2019/1/2 15:42
*/
public class ImportResult<T> {

    private List<T> repetitionData;

    private List<T> data;

    private List<String> errorMessage;

    private Boolean status;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<T> getRepetitionData() {
        return repetitionData;
    }

    public void setRepetitionData(List<T> repetitionData) {
        this.repetitionData = repetitionData;
    }
}
