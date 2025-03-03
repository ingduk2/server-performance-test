package com.duck.common.api;

public record ApiResponse(
        Result result,
        String message
) {
    public enum Result {
        SUCCESS,
        FAIL
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(Result.SUCCESS, message);
    }

    public static ApiResponse fail(String message) {
        return new ApiResponse(Result.FAIL, message);
    }
}
