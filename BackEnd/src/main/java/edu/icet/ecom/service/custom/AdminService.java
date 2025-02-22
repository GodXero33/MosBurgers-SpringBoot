package edu.icet.ecom.service.custom;

import edu.icet.ecom.dto.Admin;
import edu.icet.ecom.service.SuperService;

public interface AdminService extends SuperService {
	Admin get (Integer id);
	Admin getByUserName (String name);
}
