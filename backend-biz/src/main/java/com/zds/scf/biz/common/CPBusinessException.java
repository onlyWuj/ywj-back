
package com.zds.scf.biz.common;

/**
 *
 */
public class CPBusinessException extends RuntimeException {
    public CPBusinessException() {
    }

    public CPBusinessException(String message) {
        super(message);
    }

    public CPBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CPBusinessException(Throwable cause) {
        super(cause);
    }

    public static void throwIt(String msg) {
        throw new CPBusinessException(msg);
    }

    public static void throwIt(String msg, Throwable cause) {
        throw new CPBusinessException(msg, cause);
    }
    public static void throwIt(String param, String msg) {
        throw new CPBusinessException(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
