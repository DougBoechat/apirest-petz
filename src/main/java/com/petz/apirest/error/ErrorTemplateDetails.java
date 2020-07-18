package com.petz.apirest.error;

import javax.annotation.Generated;

public class ErrorTemplateDetails {
	
	private String title;
	private int status;
	private String detail;
	private long timestamp;
	private String developerMessage;

	@Generated("SparkTools")
	private ErrorTemplateDetails(Builder builder) {
		this.title = builder.title;
		this.status = builder.status;
		this.detail = builder.detail;
		this.timestamp = builder.timestamp;
		this.developerMessage = builder.developerMessage;
	}
	
	private ErrorTemplateDetails() {
	}
	
	
	public String getTitle() {
		return title;
	}
	public int getStatus() {
		return status;
	}
	public String getDetail() {
		return detail;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}


	/**
	 * Creates builder to build {@link ErrorTemplateDetails}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder newBuilder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link ErrorTemplateDetails}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String title;
		private int status;
		private String detail;
		private long timestamp;
		private String developerMessage;

		private Builder() {
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder status(int status) {
			this.status = status;
			return this;
		}

		public Builder detail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder timestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder developerMessage(String developerMessage) {
			this.developerMessage = developerMessage;
			return this;
		}

		public ErrorTemplateDetails build() {
			ErrorTemplateDetails resourceNotFoundDetails = new ErrorTemplateDetails();
			resourceNotFoundDetails.developerMessage = this.developerMessage;
			resourceNotFoundDetails.title = this.title;
			resourceNotFoundDetails.detail = this.detail;
			resourceNotFoundDetails.timestamp = this.timestamp;
			resourceNotFoundDetails.status = this.status;
			return resourceNotFoundDetails;
		}
	}
	
	

}
