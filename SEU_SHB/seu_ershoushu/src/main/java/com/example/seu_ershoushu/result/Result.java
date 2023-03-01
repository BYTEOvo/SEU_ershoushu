package com.example.seu_ershoushu.result;

public class Result {

    private int code;
    private String message;
    private  Object result;

    public Result(int resultCode, String message, Object data)
    {
        this.code=resultCode;
        this.message=message;
        this.result=data;
    }

    public int getCode()
    {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult()
    {
        return result;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setResult(Object result)
    {
        this.result = result;
    }
}
