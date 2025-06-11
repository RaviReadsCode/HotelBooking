package com.booking.hotelapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<ApiError> handleCityNotFound(CityNotFoundException ex) {
		ApiError error = new ApiError(404, "City Not Found", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HotelNotFoundException.class)
	public ResponseEntity<ApiError> handleHotelNotFound(HotelNotFoundException ex) {
		ApiError error = new ApiError(404, "Hotel Not Found", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MissingDataException.class)
	public ResponseEntity<ApiError> handleMissingData(MissingDataException ex) {
		ApiError error = new ApiError(400, "Hotel Not Found", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("error", "Bad Request");
		response.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ApiError> handleMissingData(NullPointerException ex) {
		ApiError error = new ApiError(400, "Some required data is missing or uninitialized", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception ex) {
		ApiError error = new ApiError(500, "Internal Server Error", "Something went wrong");
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}