package vn.ecoe.exception;

public class InternalErrorException extends GenericException {

    private static final long serialVersionUID = 1L;

    public InternalErrorException(Exception e) {
        super(e);
        this.status = 500;
    }

    public InternalErrorException(String message) {
        super(message);
        this.status = 500;
    }

    public InternalErrorException(String code, String message) {
        super(code, message, 500);
    }
}
