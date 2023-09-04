package com.panayotis.gradle;

public class InstallException extends RuntimeException {
    public InstallException(String reason) {
        super(reason);
    }
}
