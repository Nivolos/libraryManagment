package de.nordakademie.iaa.library.service;

public class ResourceNotFoundException extends ServiceException {

    private static final long serialVersionUID = 1778941345005931684L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
