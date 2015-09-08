package net.leidra.restservices.services;

import java.util.Collection;
import java.util.Optional;

import net.leidra.restservices.converters.CustomerConverter;
import net.leidra.restservices.dataaccess.CustomerRepository;
import net.leidra.restservices.dtos.CustomerDto;
import net.leidra.restservices.entities.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerConverter converter;

	public Optional<CustomerDto> findByName(String name) {
		Optional<Customer> customer = customerRepository.findByName(name);
		if(customer.isPresent()) {
			return converter.toDto(customer.get());
		}
		
		return Optional.empty();
	}
	
	public Collection<CustomerDto> findAll() {
		Collection<Customer> customers = customerRepository.findAll();
		return converter.toDtoCollection(customers);
	}
	
	public CustomerDto save(CustomerDto customerDto) {
		Optional<Customer> optionalCustomer = converter.toEntity(customerDto);
		Customer customer = customerRepository.save(optionalCustomer.orElseThrow(() -> new IllegalArgumentException()));
		return converter.toDto(customer).orElseThrow(() -> new RuntimeException());
	}
	
}
