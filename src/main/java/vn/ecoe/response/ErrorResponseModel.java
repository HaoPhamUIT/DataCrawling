package vn.ecoe.response;

/**
 * @author LocLD
 *
 */
public class ErrorResponseModel extends ResponseModel {
	private String traceId;
    public ErrorResponseModel(String traceId, int status, String exception) {
        this.setStatus(status);
        this.setException(exception);
        this.setSuccessful(false);
        this.setMsg("Unsuccessful!");
        this.setTraceId(traceId);
    }
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
    
}
