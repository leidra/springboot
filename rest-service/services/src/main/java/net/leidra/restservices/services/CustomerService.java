package net.leidra.restservices.services;

import java.util.List;

import net.leidra.restservices.dataaccess.CustomerRepository;
import net.leidra.restservices.entities.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository; 

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}
	
	public void save(Customer customer) {
		customerRepository.save(customer);
	}
	
}
