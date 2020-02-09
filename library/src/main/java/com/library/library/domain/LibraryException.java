package com.library.library.domain;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/9 15:27
 */
public class LibraryException extends RuntimeException {

    public LibraryException() {
    }

    public LibraryException(String message) {
        super(message);
    }

    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryException(Throwable cause) {
        super(cause);
    }
}
