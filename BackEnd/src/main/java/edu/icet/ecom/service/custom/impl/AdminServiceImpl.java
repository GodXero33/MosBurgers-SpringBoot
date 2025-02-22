package edu.icet.ecom.service.custom.impl;

import edu.icet.ecom.dto.Admin;
import edu.icet.ecom.entity.AdminEntity;
import edu.icet.ecom.repository.custom.AdminRepository;
import edu.icet.ecom.service.custom.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	private final AdminRepository adminRepository;
	private final ModelMapper mapper;

	@Override
	public Admin get (Integer id) {
		final AdminEntity adminEntity = this.adminRepository.get(id);

		if (adminEntity == null) return null;

		return this.mapper.map(adminEntity, Admin.class);
	}

	@Override
	public Admin getByUserName (String name) {
		final AdminEntity adminEntity = this.adminRepository.getByUserName(name);

		if (adminEntity == null) return null;

		return this.mapper.map(adminEntity, Admin.class);
	}
}
