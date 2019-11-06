package vn.ecoe.exception;

public class SequenceException extends GenericException {
	private static final long serialVersionUID = 1L;

	/**
	 * Instance with message
	 *
	 * @param message
	 */
	public SequenceException(String message) {
        super(message);
		this.status = 404;
    }

	/**
	 * Instance to create default message when not found resource by entity id.
	 *
	 * @param clazz
	 * @param id
	 */
	public SequenceException(Class<? extends Object> clazz, Long id) {
		this(clazz.getSimpleName(), "id", id);
	}

	/**
	 * Instance to create default message when not found resource.
	 *
	 * @param resource resource name
	 * @param field resource field
	 * @param value resource value
	 */
	public SequenceException(String resource, String field, Object value) {
		super(String.format("Cannot be found %s by [%s=%s]", resource, field, value.toString()));
	}

}
