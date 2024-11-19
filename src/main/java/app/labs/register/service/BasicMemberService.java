package app.labs.register.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.register.dao.MemberRepository;
import app.labs.register.model.Member;

@Service
public class BasicMemberService implements MemberService {

	@Autowired
	MemberRepository memberRepository;

	@Override
	public int getMemberCount() {
		return memberRepository.getMemberCount();
	}

	@Override
	public int getMemberCount(int deptid) {
		return memberRepository.getMemberCount(deptid);
	}

	@Override
	public List<Member> getMemberList() {
		return memberRepository.getMemberList();
	}

	@Override
	public Member getMemberInfo(int empid) {
		return memberRepository.getMemberInfo(empid);
	}

	
	
	  public void updateMember(Member member) {
	  memberRepository.updateMember(member.getUserName(), member.getEmail(),
	  member.getPhoneNumber(), member.getUserId()); }
	 
	 

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
