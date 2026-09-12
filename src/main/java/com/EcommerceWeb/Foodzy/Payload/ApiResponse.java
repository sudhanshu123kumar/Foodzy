package com.EcommerceWeb.Foodzy.Payload;

import com.EcommerceWeb.Foodzy.Enum.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

   private String message;
   private ResponseStatus status;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private T data;

}
