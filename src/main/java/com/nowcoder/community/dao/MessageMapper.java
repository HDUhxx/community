package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    //查询当前用户的会话列表，针对每个会话只返回最新的私信
    List<Message> selectConversation(int userId,int offset,int limit);

    //查询当前用户的会话数量
    int selectConversationCount(int userId);

    //查询某个会话的私信列表
    List<Message> selectLetters(String conversationId,int offset,int limit);

    //查询每个绘画包含的私信数量
    int selectLetterCount(String conversationId);

    //查询未读私信的数量
    int selectoLetterUnreadCount(int userId,String conversationId);


    //增加私信
    int insertMessage(Message message);

    //修改消息的状态
    int updateStatus(List<Integer> ids,int status);
}
