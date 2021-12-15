package com.admin.model;

import java.util.List;

public interface AdminDAO {
	// 此介面定義對資料庫的相關存取抽象方法
		void add(AdminVO adminVO);
		void update(AdminVO adminVO);
		void delete(Integer adminId);
		AdminVO findByPK(Integer adminId);
		List<AdminVO> getAll();
}
