package edu.icet.ecom.repository.custom;

import edu.icet.ecom.entity.CustomerEntity;
import edu.icet.ecom.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity> {
	Integer addAndGetID (CustomerEntity entity);
}
