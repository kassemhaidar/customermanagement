package com.codechallenge.customermanagement.business.domain.exception;

/**
 * Custom exception class used to throw with a message.
 * 
 * TODO CustomException should be moved to a common library and not implemented in every project.
 */
public class CustomException extends Exception {

	private static final long serialVersionUID = 7687546774444074571L;

	/**
     * Constructs a new custom exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     * 
     * TODO custom message must not only return the message
     * it must return a new object containing the message and error code and status
     */
	public CustomException(String message) {
        super(message);
    }
}
