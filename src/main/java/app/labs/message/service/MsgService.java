package app.labs.message.service;

import java.util.List;
import app.labs.message.model.Msg;

public interface MsgService {
    List<Msg> getSentMessages(String senderId);
    List<Msg> getReceivedMessages(String receiverId);
    void updateMessageReadStatus(int messageId);
    Msg getMsgDetail(int messageId, String viewerId);  // 메시지 상세 조회
}
