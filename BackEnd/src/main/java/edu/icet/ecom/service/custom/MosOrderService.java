package edu.icet.ecom.service.custom;

import edu.icet.ecom.dto.MosOrder;
import edu.icet.ecom.service.SuperService;

public interface MosOrderService extends SuperService {
	boolean add (MosOrder mosOrder);
}
