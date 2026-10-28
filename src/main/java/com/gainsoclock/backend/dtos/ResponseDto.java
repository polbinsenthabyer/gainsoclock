package com.gainsoclock.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gainsoclock.backend.types.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private ResponseType responseType;
    private String code;
    private Object data;
    private String message;
    private String className;
    private String methodName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date responseTime;

    @JsonIgnore
    public Boolean isSuccess(){
        return responseType == ResponseType.SUCCESS;
    }

    @JsonIgnore
    public Boolean isFail(){
        return responseType == ResponseType.ERROR;
    }

}
