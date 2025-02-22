package edu.icet.ecom.repository.custom.impl;

import edu.icet.ecom.entity.FoodItemEntity;
import edu.icet.ecom.repository.custom.FoodItemRepository;
import edu.icet.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodItemRepositoryImpl implements FoodItemRepository {
	@Override
	public boolean add (FoodItemEntity entity) {
		return false;
	}

	@Override
	public boolean update (FoodItemEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public FoodItemEntity get (Integer id) {
		return null;
	}

	@Override
	public List<FoodItemEntity> getAll () {
		final List<FoodItemEntity> foodItemEntities = new ArrayList<>();

		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT item_id, name, price, discount, category FROM food_item");

			while (resultSet.next()) foodItemEntities.add(FoodItemEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				price(resultSet.getDouble(3)).
				discount(resultSet.getDouble(4)).
				category(resultSet.getString(5)).
				build());
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return foodItemEntities;
	}
}
