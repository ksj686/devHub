package app.labs.register.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.labs.register.dao.MemberRepository;
import app.labs.register.model.Member;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class BasicMemberService implements MemberService {

	@Autowired
	MemberRepository memberRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(BasicMemberService.class);


	@Override
	public int getMemberCount() {
	    return memberRepository.getMemberCount();
	}

	public int getMemberCount(String userName) {
		return memberRepository.getMemberCount(userName);
	}

	@Override
	public List<Member> getMemberList() {
		return memberRepository.getMemberList();
	}


	
	public Member getMemberInfo(String userId) {
		return memberRepository.getMemberInfo(userId);
	}

	
	
	@Override
    public void updateMember(Member member) {
        logger.debug("Updating member: {}", member);
        memberRepository.updateMember(member.getUserName(), member.getEmail(), member.getPhoneNumber(), member.getUserId());
        logger.debug("Member updated: {}", member);
    }
	 
	 

	public void insertMember(Member member) {
		System.out.println("Inserting member: " + member);
		memberRepository.insertMember(member);
	}

	@Override
	public Member findByUserId(String userId) {
		// TODO Auto-generated method stub
		return memberRepository.findByUserId(userId);
	}

	public Member loginMember(Member member) {
		// TODO Auto-generated method stub
		return memberRepository.loginMember(member);
	}

	@Override
	public List<Map<String, Object>> getAllUserIds() {
		return memberRepository.getAllUserIds();
	}

	@Override
	public List<Map<String, Object>> getAllEmails() {
		return memberRepository.getAllEmails();
	}

	@Override
	public List<Map<String, Object>> getAllPhoneNumbers() {
		return memberRepository.getAllPhoneNumbers();
	}

	 @Override
	    public String findUserIdByEmailOrPhone(String emailOrPhone) {
	        try {
	            Member member = memberRepository.findByEmailOrPhone(emailOrPhone);
	            return member != null ? member.getUserId() : null;
	        } catch (Exception e) {
	            System.err.println("Error finding userId: " + e.getMessage());
	            return null;
	        }
	    }

	  @Override
	    public String findPasswordByEmailOrPhone(String emailOrPhone) {
	        try {
	            Member member = memberRepository.findByEmailOrPhone(emailOrPhone);
	            return member != null ? member.getUserPwd() : null;
	        } catch (Exception e) {
	            System.err.println("Error finding password: " + e.getMessage());
	            return null;
	        }
	    }

	  public boolean isUserIdDuplicated(String userId) {
	        logger.debug("Checking if userId exists: {}", userId);
	        int exists = memberRepository.existsByUserId(userId);
	        boolean tf; 
	        if (exists > 0) tf = true; 
	        else   tf = false; 
	        logger.debug("UserId exists: {}", exists);
	        return tf;
	    }

	@Override
	public boolean existsByUserId(String userId) {
		// TODO Auto-generated method stub
		return false;
	}
	 


	
}

//	@Override
//	public int deleteMember(int empid, String email) {
//		memberRepository.deleteJobHistory(empid);
//		return memberRepository.deleteMember(empid, email);
//	}

//	@Override
//	public void deleteMember(Long idx) {
//		// TODO Auto-generated method stub
//		
//	}
