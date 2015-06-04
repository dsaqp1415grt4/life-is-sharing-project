package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model;

public class LifeissharingError {
	private int status;
	private String message;

	public LifeissharingError() {
		super();
	}

	public LifeissharingError(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}