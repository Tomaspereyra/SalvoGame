package com.codeoftheweb.salvo.dto;

public class ResponseDto {
    private String cod;
    private String status;
    private Object data;


    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "cod='" + cod + '\'' +
                ", status='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
