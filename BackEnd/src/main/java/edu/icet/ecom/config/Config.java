package edu.icet.ecom.config;

import edu.icet.ecom.repository.custom.AdminRepository;
import edu.icet.ecom.repository.custom.CustomerRepository;
import edu.icet.ecom.repository.custom.FoodItemRepository;
import edu.icet.ecom.repository.custom.MosOrderRepository;
import edu.icet.ecom.repository.custom.impl.AdminRepositoryImpl;
import edu.icet.ecom.repository.custom.impl.CustomerRepositoryImpl;
import edu.icet.ecom.repository.custom.impl.FoodItemRepositoryImpl;
import edu.icet.ecom.repository.custom.impl.MosOrderRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	@Bean
	public ModelMapper getModelMapper () {
		return new ModelMapper();
	}

	@Bean
	public AdminRepository getAdminRepository () {
		return new AdminRepositoryImpl();
	}

	@Bean
	public CustomerRepository getCustomerRepository () {
		return new CustomerRepositoryImpl();
	}

	@Bean
	public FoodItemRepository getFoodItemRepository () {
		return new FoodItemRepositoryImpl();
	}

	@Bean
	public MosOrderRepository getMosOrderRepository () {
		return new MosOrderRepositoryImpl();
	}
}
