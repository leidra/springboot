package net.leidra.restservices.dataaccess;

import java.util.Optional;

import net.leidra.restservices.entities.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByName(String name);
}