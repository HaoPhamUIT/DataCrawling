package vn.ecoe.exception;

public abstract class GenericException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected int status = 500;

    protected String code;

    public GenericException() {
        super("Internal Server Error");
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, int status) {
        super(message);
        this.status = status;
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public GenericException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

    public GenericException(String code, String message, int status) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }


}
