package de.nordakademie.iaa.library.service;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -6131101780967374928L;

    public ServiceException(String message) {
        super(message);
    }
}
