package net.leidra.restservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4695025615830553534L;

	public CustomerNotFoundException(String customerId) {
		super("could not find customer '" + customerId + "'.");
	}
}
