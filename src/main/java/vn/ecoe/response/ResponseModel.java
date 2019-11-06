package vn.ecoe.response;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LocLD
 *
 */
public class ResponseModel {
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private final String serverDateTime = format.format(new Date());

	private int status = 200;
	private String msg = "Successful";
	private String exception;
	private boolean isSuccessful = true;
	private Object data;

	public ResponseModel() {
		super();
	}

	public ResponseModel(Object data) {
		super();
		this.data = data;
	}

	public ResponseModel(int status, Object data) {
		this.status = status;
		this.data = data;
	}

	public ResponseModel(int status, String msg, boolean isSuccessful) {
		this.status = status;
		this.msg = msg;
		this.isSuccessful = isSuccessful;
	}

	public String getServerDateTime() {
		return serverDateTime;
	}

	public String getException() {
		return exception;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean successful) {
		isSuccessful = successful;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
