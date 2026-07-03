package com.yash.jobportal.exception;

// Extends the existing BadRequestException so it's automatically caught by
// your existing GlobalExceptionHandler's handleBadRequest() - no new
// @ExceptionHandler method needed there. Maps to 400, not a dedicated 409,
// which is a reasonable simplification given the existing exception set.
public class DuplicateApplicationException extends BadRequestException{
    public DuplicateApplicationException(String message){
        super(message);
    }
}
