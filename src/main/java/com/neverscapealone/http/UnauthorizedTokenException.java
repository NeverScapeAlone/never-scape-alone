package com.neverscapealone.http;

import java.io.IOException;

/**
 * Exception for when a tokenized route in {@link NeverScapeAloneWebsocket} fails due to using a bad or unauthorized token.
 */
public class UnauthorizedTokenException extends IOException {
    public UnauthorizedTokenException(String message) {
        super(message);
    }
}
