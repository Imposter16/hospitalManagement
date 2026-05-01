package com.springboot.project.hospitalManagement.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "message", "pagination", "data" })
public class ApiResponse<T> {
	 private boolean status;     
	    private String message;    
	    private Object pagination;  
	    private T data;  
}
