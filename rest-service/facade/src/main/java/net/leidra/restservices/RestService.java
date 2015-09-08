package net.leidra.restservices;

import java.util.Arrays;
import java.util.List;

import net.leidra.restservices.dataaccess.CustomerRepository;
import net.leidra.restservices.entities.Customer;
import net.leidra.restservices.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class RestService {
	@Autowired
	private CustomerService customerService; 

	@RequestMapping(value="customers", method=RequestMethod.GET)
	public List<Customer> findAll() {
		return customerService.findAll();
	}
	
	@RequestMapping(value="customer", method=RequestMethod.POST)
	public void save(Customer customer) {
		customerService.save(customer);
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository customerRepository) {
		return (evt) -> Arrays.asList("Customer one,Customer two,Customer three".split(","))
				.parallelStream().forEach(name -> customerRepository.save(new Customer((long)name.length(), name)));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RestService.class, args);
	}
}