package app.labs.register.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import app.labs.register.model.Member;

@Mapper
public interface MemberRepository {
	int getMemberCount();
	int getMemberCount(@Param("User_id") String userName);
	List<Member> getMemberList();
	Member getMemberInfo(String userId);
	void updateMember(@Param("userName") String userName, @Param("email") String email, @Param("phoneNumber") String phoneNumber, @Param("userId") String userId);
	void insertMember(Member member);
	//void deleteJobHistory(int memberid);
	//int deleteMember(@Param("memberid") int memberid, @Param("email") String email);
	List<Map<String, Object>> getAllUserIds();
	List<Map<String, Object>> getAllEmails();
	List<Map<String, Object>> getAllPhoneNumbers();
	Member loginMember(Member member);
    
    Member findByEmailOrPhone(@Param("emailOrPhone") String emailOrPhone);
	Member findByUserId(String userId);
	int existsByUserId(String userId);
   }
   
   
       
      
   
