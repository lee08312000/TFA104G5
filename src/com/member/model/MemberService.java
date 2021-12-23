package com.member.model;

import java.util.List;

public class MemberService {
	private MemberDAO dao;
	
	public MemberService() {
		dao = new MemberDAOImpl();
	}
	
	public MemberVO addMember(Integer memberAccountStatus, String memberName, 
			String memberAccount, String memberPassword, String memberEmail, String memberAddress, 
			String memberPhone, byte[] memberPic) {
		
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMemberAccountStatus(memberAccountStatus);
		memberVO.setMemberName(memberName);
		memberVO.setMemberAccount(memberAccount);
		memberVO.setMemberPassword(memberPassword);
		memberVO.setMemberEmail(memberEmail);
		memberVO.setMemberAddress(memberAddress);
		memberVO.setMemberPhone(memberPhone);
		memberVO.setMemberPic(memberPic);
		
		dao.add(memberVO);
		return memberVO;
	}	
	
	public MemberVO updateMember(Integer memberId, Integer memberAccountStatus, String memberName, 
			String memberAccount, String memberPassword, String memberEmail, String memberAddress, 
			String memberPhone, byte[] memberPic) {
		
		MemberVO memberVO = dao.findByPK(memberId);
		memberVO.setMemberId(memberId);
		memberVO.setMemberAccountStatus(memberAccountStatus);
		memberVO.setMemberName(memberName);
		memberVO.setMemberAccount(memberAccount);
		memberVO.setMemberPassword(memberPassword);
		memberVO.setMemberEmail(memberEmail);
		memberVO.setMemberAddress(memberAddress);
		memberVO.setMemberPhone(memberPhone);	
		if (memberPic != null) {
			memberVO.setMemberPic(memberPic);
		}
		dao.update(memberVO);
		return memberVO;
	}	
	
	public void deleteMember(Integer memberId) {
		dao.delete(memberId);
	}
	
	public MemberVO getOneMember(Integer memberId) {
		return dao.findByPK(memberId);
	}
	
	public List<MemberVO> getAllMember() {
		return dao.getAll();
	}
	
	public MemberVO login(String memberAccount, String memberPassword) {
		return dao.login(memberAccount, memberPassword);
	}
	
	public MemberVO resetPasswordCheck(String memberAccount, String memberEmail) {
		return dao.resetPasswordCheck(memberAccount, memberEmail);
	}
	
}
