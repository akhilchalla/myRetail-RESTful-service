package com.example.myretail.exception.response;

import com.example.myretail.util.AppConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    @Pattern(regexp = AppConstant.DATA_PATTERN)
    private String msg;

    @Pattern(regexp = AppConstant.DATA_PATTERN)
    private String code;

}
