package net.leidra.restservices;

import java.util.Arrays;
import java.util.Collection;

import net.leidra.restservices.dtos.CustomerDto;
import net.leidra.restservices.exceptions.CustomerNotFoundException;
import net.leidra.restservices.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class RestService {
	@Autowired
	private CustomerService customerService; 

	@RequestMapping(value="customers", method=RequestMethod.GET)
	public Collection<CustomerDto> findAll() {
		return customerService.findAll();
	}
	
	@RequestMapping(value="customer/{customerName}", method=RequestMethod.GET)
	public CustomerDto find(@PathVariable String customerName) {
		return customerService.findByName(customerName).orElseThrow(() -> new CustomerNotFoundException(customerName));
	}
	
	@RequestMapping(value="customer", method=RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody CustomerDto customer) {
		CustomerDto result = customerService.save(customer);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}

	@Bean
	public CommandLineRunner loadData() {
		return (evt) -> Arrays.asList("Customer one,Customer two,Customer three".split(","))
				.stream().forEach(name -> customerService.save(new CustomerDto((long)name.length(), name)));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RestService.class, args);
	}
}