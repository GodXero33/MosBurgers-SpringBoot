package edu.icet.ecom.service.custom.impl;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.entity.CustomerEntity;
import edu.icet.ecom.repository.custom.CustomerRepository;
import edu.icet.ecom.service.custom.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;
	private final ModelMapper mapper;

	@Override
	public List<Customer> getAll () {
		final List<CustomerEntity> customerEntities = this.customerRepository.getAll();
		final List<Customer> customers = new ArrayList<>();

		customerEntities.forEach(customer -> customers.add(this.mapper.map(customer, Customer.class)));

		return customers;
	}

	@Override
	public Customer get (Integer id) {
		final CustomerEntity customerEntity = this.customerRepository.get(id);

		if (customerEntity == null) return null;

		return this.mapper.map(customerEntity, Customer.class);
	}

	@Override
	public Integer addAndGetID (Customer customer) {
		return this.customerRepository.addAndGetID(this.mapper.map(customer, CustomerEntity.class));
	}
}
