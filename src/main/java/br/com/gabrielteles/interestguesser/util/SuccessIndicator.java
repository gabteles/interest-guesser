package br.com.gabrielteles.interestguesser.util;

import io.swagger.annotations.ApiModelProperty;

public class SuccessIndicator {
	@ApiModelProperty(notes = "The success indication", required = true)
	private boolean success;
	
	public static SuccessIndicator indicate(boolean success) {
		return new SuccessIndicator(success);
	}
	
	public SuccessIndicator(boolean success) {
		this.success = success;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
}
