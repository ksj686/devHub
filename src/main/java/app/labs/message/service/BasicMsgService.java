package app.labs.message.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.labs.message.dao.MsgRepository;
import app.labs.message.model.Msg;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BasicMsgService implements MsgService {

    @Autowired
    private MsgRepository msgRepository;

    @Override
    public List<Msg> getSentMessages(String userId) {
        return msgRepository.getSentMessages(userId);
    }
    
	@Override
	public List<Msg> sentMsgSearch(Map<String, Object> params) {
//		return msgRepository.sentMsgSearch(userId, searchFilter, searchQuery);
		return msgRepository.sentMsgSearch(params);
	}

    @Override
    public List<Msg> getReceivedMessages(String userId) {
        return msgRepository.getReceivedMessages(userId);
    }
    
	@Override
	public List<Msg> receivedMsgSearch(Map<String, Object> params) {
		return msgRepository.receivedMsgSearch(params);
	}
    
    @Override
    public Msg getSentMsgDetail(String messageId, String viewerId) {
        Msg msg = msgRepository.getSentMsgDetail(messageId);
        return msg;
    }

    @Override
    @Transactional
    public Msg getReceivedMsgDetail(String messageId, String viewerId) {
        Msg msg = msgRepository.getReceivedMsgDetail(messageId);
        int queryAct = 0;
        // 메시지 조회 시 수신자가 현재 사용자일 경우 읽음 상태 업데이트
//        if (msg != null && msg.getReceiverId().equals(viewerId) && msg.getDateRead() == null) {
		if (msg != null && msg.getDateRead() == null) {
			queryAct += msgRepository.updateSentMsgReadStatus(messageId);  // 수신일자 갱신
			queryAct += msgRepository.updateReceivedMsgReadStatus(messageId);  // 수신일자 갱신
			msg = msgRepository.getReceivedMsgDetail(messageId);
        }
        log.info(queryAct + "개 업데이트 쿼리 실행 완료");
        return msg;
    }

	@Override
	@Transactional
	public int insertMsg(Msg msg) {
		int a = msgRepository.insertReceivedMsg(msg);
		a += msgRepository.insertSentMsg(msg);
		return a;
	}

	@Override
	@Transactional
	public int deleteSent(List<String> messageIds) {
		int[] cnt = {0};
		messageIds.forEach(messageId -> {
			msgRepository.deleteSent(messageId);
			cnt[0]++;
		});
		return cnt[0];
	}

	@Override
	@Transactional
	public int deleteReceived(List<String> messageIds) {
		int[] cnt = {0};
		messageIds.forEach(messageId -> {
			msgRepository.deleteReceived(messageId);
			cnt[0]++;
		});
		return cnt[0];
	}

	@Override
	public void updateExpiredMember() {
		msgRepository.updateExpiredMember();
	}
    
//    @Override
//    public Msg getMsgDetail(int messageId, String viewerId) {
//    	Msg msg = msgRepository.getMsgDetail(messageId);
//        
//        // 메시지를 조회할 때 dateRead가 NULL이면 자동으로 현재 시간을 설정
//        if (msg != null && msg.getReceiverId().equals(viewerId) && msg.getDateRead() == null) {
//            updateMessageReadStatus(messageId);  // 날짜 갱신
//        }
//        
//        msg = msgRepository.getMsgDetail(messageId);
//        
//        return msg;
//    }
}
