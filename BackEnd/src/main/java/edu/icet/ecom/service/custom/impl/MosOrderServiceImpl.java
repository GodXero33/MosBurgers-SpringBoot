package edu.icet.ecom.service.custom.impl;

import edu.icet.ecom.dto.MosOrder;
import edu.icet.ecom.entity.MosOrderEntity;
import edu.icet.ecom.repository.custom.MosOrderRepository;
import edu.icet.ecom.service.custom.MosOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class MosOrderServiceImpl implements MosOrderService {
	private final MosOrderRepository mosOrderRepository;
	private final ModelMapper mapper;

	@Override
	public boolean add(MosOrder mosOrder) {
		return this.mosOrderRepository.add(this.mapper.map(mosOrder, MosOrderEntity.class));
	}
}
