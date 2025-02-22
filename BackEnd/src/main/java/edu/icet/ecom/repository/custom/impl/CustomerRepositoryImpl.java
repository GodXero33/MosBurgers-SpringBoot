package edu.icet.ecom.repository.custom.impl;

import edu.icet.ecom.entity.CustomerEntity;
import edu.icet.ecom.repository.custom.CustomerRepository;
import edu.icet.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
	@Override
	public boolean add (CustomerEntity entity) {
		return false;
	}

	@Override
	public boolean update (CustomerEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public CustomerEntity get (Integer id) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT customer_id, name, phone, email, address FROM customer WHERE customer_id = ?", id);

			if (resultSet.next()) return CustomerEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				phone(resultSet.getString(3)).
				email(resultSet.getString(4)).
				address(resultSet.getString(5)).
				build();
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return null;
	}

	@Override
	public List<CustomerEntity> getAll () {
		final List<CustomerEntity> customerEntities = new ArrayList<>();

		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT customer_id, name, phone, email, address FROM customer");

			while (resultSet.next()) customerEntities.add(CustomerEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				phone(resultSet.getString(3)).
				email(resultSet.getString(4)).
				address(resultSet.getString(5)).
				build());
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return customerEntities;
	}

	@Override
	public Integer addAndGetID (CustomerEntity entity) {
		try {
			return CrudUtil.executeWithGeneratedKeys(
				"INSERT INTO customer (name, phone, email, address) VALUES (?, ?, ?, ?)",
				entity.getName(),
				entity.getPhone(),
				entity.getEmail(),
				entity.getAddress()
			);
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return 0;
		}
	}
}
