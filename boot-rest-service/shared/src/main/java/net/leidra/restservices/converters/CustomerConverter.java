package net.leidra.restservices.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.leidra.restservices.dtos.CustomerDto;
import net.leidra.restservices.entities.Customer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerConverter {
	public Optional<CustomerDto> toDto(Customer entity) {
		if(entity == null) {
			return Optional.empty();
		}
		
		CustomerDto dto = new CustomerDto();
		BeanUtils.copyProperties(entity, dto);
		
		return Optional.ofNullable(dto);
	}

	public Collection<CustomerDto> toDtoCollection(Collection<Customer> entities) {
		if(entities == null) {
			return Collections.emptyList();
		}
		
		List<CustomerDto> dtos = new ArrayList<CustomerDto>();
		entities.stream().forEach(entity -> {
			CustomerDto dto = new CustomerDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		});
		
		return dtos;
	}

	public Optional<Customer> toEntity(CustomerDto dto) {
		if(dto == null) {
			return Optional.empty();
		}
		
		Customer entity = new Customer();
		BeanUtils.copyProperties(dto, entity);
		
		return Optional.ofNullable(entity);
	}

	public Collection<Customer> toEntityCollection(Collection<CustomerDto> dtos) {
		if(dtos == null) {
			return Collections.emptyList();
		}
		
		List<Customer> entities = new ArrayList<Customer>();
		dtos.stream().forEach(dto -> {
			Customer entity = new Customer();
			BeanUtils.copyProperties(dto, entity);
			entities.add(entity);
		});
		
		return entities;
	}

}