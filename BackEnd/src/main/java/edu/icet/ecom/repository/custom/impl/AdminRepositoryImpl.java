package edu.icet.ecom.repository.custom.impl;

import edu.icet.ecom.entity.AdminEntity;
import edu.icet.ecom.repository.custom.AdminRepository;
import edu.icet.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {
	@Override
	public boolean add (AdminEntity entity) {
		return false;
	}

	@Override
	public boolean update (AdminEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public AdminEntity get (Integer id) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT admin_id, name, phone, salary, email, password, position, dob, address FROM admin WHERE admin_id = ?", id);

			if (resultSet.next()) return AdminEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				phone(resultSet.getString(3)).
				salary(resultSet.getDouble(4)).
				email(resultSet.getString(5)).
				password(resultSet.getString(6)).
				position(resultSet.getString(7)).
				dob(resultSet.getString(8)).
				address(resultSet.getString(9)).
				build();
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return null;
	}

	@Override
	public List<AdminEntity> getAll () {
		return List.of();
	}

	@Override
	public AdminEntity getByUserName (String name) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT admin_id, name, phone, salary, email, password, position, dob, address FROM admin WHERE name = ?", name);

			if (resultSet.next()) return AdminEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				phone(resultSet.getString(3)).
				salary(resultSet.getDouble(4)).
				email(resultSet.getString(5)).
				password(resultSet.getString(6)).
				position(resultSet.getString(7)).
				dob(resultSet.getString(8)).
				address(resultSet.getString(9)).
				build();
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return null;
	}
}
