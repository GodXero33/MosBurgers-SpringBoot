package edu.icet.ecom.repository.custom.impl;

import edu.icet.ecom.entity.MosOrderEntity;
import edu.icet.ecom.entity.OrderItemEntity;
import edu.icet.ecom.repository.custom.MosOrderRepository;
import edu.icet.ecom.util.CrudUtil;
import edu.icet.ecom.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MosOrderRepositoryImpl implements MosOrderRepository {

	@Override
	public boolean add (MosOrderEntity entity) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			connection.setAutoCommit(false);

			final int orderId = CrudUtil.executeWithGeneratedKeys(
				"INSERT INTO mos_order (customer_id, admin_id, place_date, total_amount, discount, final_amount) VALUES (?, ?, ?, ?, ?, ?)",
				entity.getCustomerId(),
				entity.getAdminId(),
				entity.getPlaceDate(),
				entity.getTotalAmount(),
				entity.getDiscount(),
				entity.getFinalAmount()
			);

			if (orderId <= 0) {
				connection.rollback();
				return false;
			}

			final List<OrderItemEntity> orderItems = entity.getItems();

			for (final OrderItemEntity orderItemEntity : orderItems) {
				CrudUtil.execute(
					"INSERT INTO order_item (item_id, order_id, quantity, total_price, price_per_unit) VALUES (?, ?, ?, ?, ?)",
					orderItemEntity.getFoodItemId(),
					orderId,
					orderItemEntity.getQuantity(),
					orderItemEntity.getTotalPrice(),
					orderItemEntity.getPricePerUnit()
				);
			}

			connection.commit();

			return true;
		} catch (SQLException exception) {
			try {
				DBConnection.getInstance().getConnection().rollback();
			} catch (SQLException rollbackException) {
				System.out.println(rollbackException.getMessage());
			}

			return false;
		} finally {
			try {
				DBConnection.getInstance().getConnection().setAutoCommit(true);
			} catch (SQLException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}

	@Override
	public boolean update (MosOrderEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public MosOrderEntity get (Integer id) {
		return null;
	}

	@Override
	public List<MosOrderEntity> getAll () {
		return List.of();
	}
}
