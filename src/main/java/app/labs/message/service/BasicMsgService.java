package app.labs.message.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.message.dao.MsgRepository;
import app.labs.message.model.Msg;

@Service
public class BasicMsgService implements MsgService {

    @Autowired
    private MsgRepository msgRepository;

    @Override
    public List<Msg> getSentMessages(String senderId) {
        return msgRepository.getSentMessages(senderId);
    }

    @Override
    public List<Msg> getReceivedMessages(String receiverId) {
        return msgRepository.getReceivedMessages(receiverId);
    }

    @Override
    public void updateMessageReadStatus(int messageId) {
    	msgRepository.updateMessageReadStatus(messageId);
    }
    
    @Override
    public Msg getMsgDetail(int messageId, String viewerId) {
    	Msg msg = msgRepository.getMsgDetail(messageId);
        
        // 메시지를 조회할 때 dateRead가 NULL이면 자동으로 현재 시간을 설정
        if (msg != null && msg.getReceiverId().equals(viewerId) && msg.getDateRead() == null) {
            updateMessageReadStatus(messageId);  // 날짜 갱신
        }
        
        msg = msgRepository.getMsgDetail(messageId);
        
        return msg;
    }
}
