package com.riki.simsppob.exception;

import java.io.Serial;

public class ResourceAlreadyExistedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExistedException(String msg) {
        super(msg);
    }
}
