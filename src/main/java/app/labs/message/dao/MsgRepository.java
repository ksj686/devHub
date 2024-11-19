package app.labs.message.dao;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.message.model.Msg;

@Mapper
public interface MsgRepository {

    List<Msg> getSentMessages(@Param("userId") String userId);
    List<Msg> getReceivedMessages(@Param("userId") String userId);
    Msg getSentMsgDetail(@Param("messageId") String messageId);  // 보낸 메시지 상세 조회
    Msg getReceivedMsgDetail(@Param("messageId") String messageId);  // 받은 메시지 상세 조회
//    Msg getMsgDetail(@Param("messageId") int messageId);  // 메시지 상세 조회
    int insertReceivedMsg(Msg msg);
    int insertSentMsg(Msg msg);
    int updateSentMsgReadStatus(@Param("messageId") String messageId);
    int updateReceivedMsgReadStatus(@Param("messageId") String messageId);
    int deleteSent(String messageId);
    int deleteReceived(String messageId);
}
