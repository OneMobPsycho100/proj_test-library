package com.dio.frame.support.exception;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/9 15:27
 */
public class DioException extends RuntimeException {

    public DioException() {
    }

    public DioException(String message) {
        super(message);
    }

    public DioException(String message, Throwable cause) {
        super(message, cause);
    }

    public DioException(Throwable cause) {
        super(cause);
    }
}
