package com.EcommerceWeb.Foodzy.Exceptions;

import com.EcommerceWeb.Foodzy.Enum.ResponseStatus;
import com.EcommerceWeb.Foodzy.Payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ApiResponse<Void>> resourcesNotFoundExceptionHandler(ResourceNotFoundException ex){

       String message = ex.getMessage();
       ApiResponse<Void> apiResponse = new ApiResponse(message, ResponseStatus.NOT_FOUND, null);
       return new ResponseEntity<ApiResponse<Void>>(apiResponse, HttpStatus.NOT_FOUND);
   }

    @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApiResponse<Void>> methodArgumentNotValidExceptionHandler(
           MethodArgumentNotValidException ex
   ){

       String message = ex.getBindingResult()
               .getFieldErrors()
               .get(0)
               .getDefaultMessage();


       ApiResponse<Void> apiResponse = new ApiResponse(message, ResponseStatus.BAD_REQUEST, null);

       return new ResponseEntity<ApiResponse<Void>>(apiResponse, HttpStatus.BAD_REQUEST);

   }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> duplicateResourceExceptionHandler(
            DuplicateResourceException ex
    ){

        String message = ex.getMessage();


        ApiResponse<Void> apiResponse =
                new ApiResponse(
                        message,
                        ResponseStatus.BAD_REQUEST,
                        null
                );


        return new ResponseEntity<ApiResponse<Void>>(
                apiResponse,
                HttpStatus.CONFLICT
        );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> globalExceptionHandler(
            Exception ex
    ){

        ex.printStackTrace();

        ApiResponse<Void> response =
                new ApiResponse(
                        ex.getMessage(),
                        ResponseStatus.INTERNAL_SERVER_ERROR,
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(
            BadCredentialsException ex) {

        ApiResponse<Void> response = new ApiResponse<>(
                "Invalid email or password",
                ResponseStatus.UNAUTHORIZED,
                null
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
}
