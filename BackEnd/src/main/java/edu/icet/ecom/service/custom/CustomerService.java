package edu.icet.ecom.service.custom;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {
	List<Customer> getAll ();
	Customer get (Integer id);
	Integer addAndGetID (Customer customer);
}
