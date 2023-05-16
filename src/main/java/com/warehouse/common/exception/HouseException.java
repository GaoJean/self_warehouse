package com.warehouse.common.exception;

import com.warehouse.common.error.ErrorCode;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/06/03 18:33
 */
public class HouseException extends Exception {
    private int code;
    private String message;
    private String i18nMessage;

    public HouseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HouseException(int code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public HouseException(int code, String message, String i18nMessage) {
        this.code = code;
        this.message = message;
        this.i18nMessage = i18nMessage;
    }

    public HouseException(int code, String message, String i18nMessage, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
        this.i18nMessage = i18nMessage;
    }

    public HouseException(int code, String message, Object... args) {
        this.code = code;
        this.message = String.format(message, args);
        if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
            this.initCause((Throwable) args[args.length - 1]);
        }

    }

    public HouseException(int code, String message, String i18nMessage, Object... args) {
        this.code = code;
        this.message = String.format(message, args);
        this.i18nMessage = String.format(i18nMessage, args);
        if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
            this.initCause((Throwable) args[args.length - 1]);
        }

    }

    public HouseException(ErrorCode errorCode, Object... args) {
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.message = String.format(errorCode.getMessage(), args);
            this.i18nMessage = String.format(errorCode.getI18nMessage(), args);
        }

        if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
            this.initCause((Throwable) args[args.length - 1]);
        }

    }

    public HouseException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
            this.i18nMessage = errorCode.getI18nMessage();
        }

    }

    public HouseException(ErrorCode errorCode) {
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
            this.i18nMessage = errorCode.getI18nMessage();
        }

    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getI18nMessage() {
        return this.i18nMessage;
    }
}
