package edu.icet.ecom.repository.custom;

import edu.icet.ecom.entity.AdminEntity;
import edu.icet.ecom.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminEntity> {
	AdminEntity getByUserName (String name);
}
