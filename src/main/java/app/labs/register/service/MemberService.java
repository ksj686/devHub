package app.labs.register.service;

import java.util.List;
import java.util.Map;

import app.labs.register.model.Member;

public interface MemberService {
	int getMemberCount();
	int getMemberCount(String userName);
	List<Member> getMemberList();
	Member getMemberInfo(String userId);
	void updateMember(Member member);
	void insertMember(Member member);
	//int deleteMember(int memberid, String email);
	List<Map<String, Object>> getAllUserIds();
	List<Map<String, Object>> getAllEmails();
	List<Map<String, Object>> getAllPhoneNumbers();
	Member loginMember(Member member);
	Member findByUserId(String userId);
	//void deleteMember(Long idx);
	
	
}