package vn.ecoe.exception;

public class RequestTimeoutException extends GenericException{

    private static final long serialVersionUID = 1L;

    /**
     * Instance with message
     * @param message
     */
    public RequestTimeoutException(String message) {
        super(message);
        this.status = 408;
    }

    /**
     * Instance with code and message
     * @param code
     * @param message
     */
    public RequestTimeoutException(String code, String message) {
        super(code, message, 408);
    }
}
