package app.labs.message.dao;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.message.model.Msg;

@Mapper
public interface MsgRepository {

    List<Msg> getSentMessages(@Param("senderId") String senderId);
    List<Msg> getReceivedMessages(@Param("receiverId") String receiverId);
    void updateMessageReadStatus(@Param("messageId") int messageId);
    Msg getMsgDetail(@Param("messageId") int messageId);  // 메시지 상세 조회
}
