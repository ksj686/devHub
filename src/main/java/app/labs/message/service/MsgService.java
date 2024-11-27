package app.labs.message.service;

import java.util.List;
import java.util.Map;

import app.labs.message.model.Msg;

public interface MsgService {
    List<Msg> getSentMessages(String userId);
//    List<Msg> sentMsgSearch(String userId, String searchFilter, String searchQuery);
    List<Msg> sentMsgSearch(Map<String, Object> params);
    List<Msg> receivedMsgSearch(Map<String, Object> params);
    List<Msg> getReceivedMessages(String userId);
//    void updateMessageReadStatus(int messageId);
    Msg getSentMsgDetail(String messageId, String viewerId);  // 보낸 메시지 상세 조회
    Msg getReceivedMsgDetail(String messageId, String viewerId);  // 받은 메시지 상세 조회
//    Msg getMsgDetail(int messageId, String viewerId);  // 메시지 상세 조회
    int insertMsg(Msg msg);
    int deleteSent(List<String> messageIds);
    int deleteReceived(List<String> messageIds);
    //schedule
//    void updateExpiredMember();
}
