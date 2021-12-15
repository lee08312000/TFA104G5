package com.member.model;

import java.util.List;

public interface MemberDAO {
	void add(MemberVO memberVO);
	void update(MemberVO memberVO);
	void delete(Integer memberId);
	MemberVO findByPK(Integer memberId);
	List<MemberVO> getAll();
	
	MemberVO login(String memberAccount, String memberPassword);
	MemberVO resetPasswordCheck(String memberAccount, String memberEmail);
	
}
