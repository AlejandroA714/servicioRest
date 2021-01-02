package com.example.servicioRest.utils;

import lombok.NoArgsConstructor;
import org.springframework.validation.ObjectError;

@NoArgsConstructor
public class Result { //Esta clase puede ser retornada desde un ResponseEntity<?> para informar el resultado de una llamada

    public Result(Boolean success,Object data){
        setSuccess(success);
        setData(data);
    }

    private Boolean Success;

    private Object data;

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
