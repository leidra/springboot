package net.leidra.restservices.services;

import java.util.Arrays;
import java.util.List;

import net.leidra.restservices.entities.Customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CustomerService {

	@RequestMapping(value="customers", method=RequestMethod.GET)
	public List<Customer> findAll() {
		return Arrays.asList(new Customer(1l, "Customer one"));
	}
	
}
