package vn.ecoe.exception;

public class AccessDeniedOnCaseException extends GenericException {

    private static final long serialVersionUID = 1L;

    public AccessDeniedOnCaseException(Exception e) {
        super(e);
        this.status = 409;
    }

    public AccessDeniedOnCaseException(String message) {
        super(message);
        this.status = 409;
    }

    public AccessDeniedOnCaseException(String code, String message) {
        super(code, message, 409);
    }
        
}
