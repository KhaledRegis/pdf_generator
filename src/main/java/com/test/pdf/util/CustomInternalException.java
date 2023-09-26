package com.test.pdf.util;

/**
 * 
 * @author Jignesh Koshti
 * 
 * @
 * Custom exception class that extends RuntimeException, to be used for "internal errors", such as - 
 * 	- failed stored procedure/function call (the procedure returned "FAILED" status)
 * 	- some file could not be found or some output stream could not be opened
 * 	- any other internal processing error
 * 
 * The class has 2 public constructors - One accepts an ErrorMessageEnum which in itself is a wrapper containing error message and error code
 * The other constructor accepts an array of strings to be appended to the error message, in addition to ErrorMessageEnum.
 * 
 * The constructors of this class invoke the super constructor so that a RuntimeException instance is created (which is required for 
 * rollback to work in case of exceptions) and the custom error message is embedded in the runtime exception.
 * 
 * The class also holds 2 instance variables for error message and error code, so that they can be derived using class instance.
 *
 */

public class CustomInternalException extends RuntimeException{
	static final long serialVersionUID = -3387815693124229350L;
	
	private String errorMessage;
	private int errorCode;
	private String funcName;
	
	/**
	 * 
	 * @param errorMessageEnum
	 */
	public CustomInternalException(ErrorMessageEnum errorMessageEnum) {
		super(errorMessageEnum.getErrorCode()+":"+errorMessageEnum.getErrorMessage());		
		this.errorMessage=errorMessageEnum.getErrorMessage();
		this.errorCode=errorMessageEnum.getErrorCode();
	}
	
	/**
	 * 
	 * @param errorMessageEnum
	 * @param appendMessage
	 */
	public CustomInternalException(ErrorMessageEnum errorMessageEnum, String funcName, String... appendMessage) {
		super(errorMessageEnum.getErrorCode()+":"+constructErrorMessage(errorMessageEnum, appendMessage));
		this.funcName=funcName;
		//this.errorMessage=constructErrorMessage(errorMessageEnum, appendMessage);
		this.errorMessage=constructErrorMessage( appendMessage);
		this.errorCode=errorMessageEnum.getErrorCode();
	}
	
	
	/*
	 * private static method, must not be made public 
	 * 
	 */
	private static String constructErrorMessage(ErrorMessageEnum errorMessageEnum, String... appendMessage) {
		String errorMessage = errorMessageEnum.getErrorMessage();
		
		if (appendMessage!=null && appendMessage.length>0) {
			errorMessage=errorMessage+" [";
			for (String message: appendMessage) {
				errorMessage=errorMessage+message+", ";
			}
			
			errorMessage=errorMessage+" ]";
			errorMessage=errorMessage.replace(",  ]", "]");
		}
		return errorMessage;
	}
	
	private static String constructErrorMessage(String... appendMessage) {
		String errorMessage = "";
		
		if (appendMessage!=null && appendMessage.length>0) {
			for (String message: appendMessage) {
				errorMessage=errorMessage+message+", ";
			}
			
			errorMessage=errorMessage+" ]";
			errorMessage=errorMessage.replace(",  ]", "");
		}
		return errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public String getFuncName() {
		return funcName;
	}

}
