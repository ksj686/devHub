package app.labs.register.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.register.model.Member;

@Mapper
public interface MemberRepository {
	int getMemberCount();
	int getMemberCount(@Param("deptid") int deptid);
	List<Member> getMemberList();
	Member getMemberInfo
	(int empid);
	void updateMember(String userName, String email, String phoneNumber, String userId);
	void insertMember(Member member);
	//void deleteJobHistory(int memberid);
	//int deleteMember(@Param("memberid") int memberid, @Param("email") String email);
	List<Map<String, Object>> getAllUserIds();
	List<Map<String, Object>> getAllEmails();
	List<Map<String, Object>> getAllPhoneNumbers();
	Member loginMember(Member member);
	Member findByUserId(String userId);
	
	
}
