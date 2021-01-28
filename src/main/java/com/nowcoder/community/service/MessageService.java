package com.nowcoder.community.service;


import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.util.SensitiveFilter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    //与所有人的会话列表
    public List<Message> findConversations(int userId,int offset,int limit){
        return messageMapper.selectConversation(userId,offset,limit);
    }

    public int findconversationCount(int userId){
        return messageMapper.selectConversationCount(userId);
    }

    //与某个人的私信列表
    public List<Message> findLetters(String conversationId,int offset,int limit){
        return messageMapper.selectLetters(conversationId,offset,limit);
    }

    public int findLetterCount(String conversationId){
        return messageMapper.selectLetterCount(conversationId);
    }

    //未读消息
    public int findLetterUnreadCount(int userId,String conversationId){
        return messageMapper.selectoLetterUnreadCount(userId,conversationId);
    }

    //添加消息
    public int addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    //设置已读
    public int readMessage(List<Integer> ids){
        return messageMapper.updateStatus(ids,1);
    }
}
