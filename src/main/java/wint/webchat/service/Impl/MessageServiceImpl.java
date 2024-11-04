package wint.webchat.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.common.Constant;
import wint.webchat.entities.conversation.Conversation;
import wint.webchat.entities.conversation.MemberConversation;
import wint.webchat.entities.conversation.Message;
import wint.webchat.google.IGoogleDriveFile;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.MessageDTO;
import wint.webchat.modelDTO.request.MediaMessageDTO;
import wint.webchat.modelDTO.request.TextMessageDTO;
import wint.webchat.repositories.IMessageRepository;
import wint.webchat.repositories.IUserRepositoryJPA;
import wint.webchat.repositories.Impl.MemberConversationRepository;
import wint.webchat.service.IConversationService;
import wint.webchat.service.IMessageService;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
    private final IMessageRepository messageRepository;
    private final IConversationService conversationService;
    private final MemberConversationRepository memberConversationRepository;
    private final IUserRepositoryJPA iUserRepositoryJPA;
    private final IGoogleDriveFile googleDriveFile;
    @Override
    public ApiResponse<List<MessageDTO>> getListMessageByConversationId(int amount, Long conversationId, int start) {
        var result = messageRepository.getListMessageByConversationId(amount, conversationId, start);
        result = result.stream()
                .sorted(Comparator
                        .comparing(MessageDTO::getTimeCall, Comparator.nullsLast(null)))
                .collect(Collectors.toList());
        if (result.isEmpty() || result.size() < amount) {
            return ApiResponse.<List<MessageDTO>>builder()
                    .error(Collections.emptyMap())
                    .data(result)
                    .code(HttpStatus.NO_CONTENT.value())
                    .build();
        }
        return ApiResponse.<List<MessageDTO>>builder()
                .error(Collections.emptyMap())
                .data(result)
                .code(HttpStatus.OK.value())
                .build();
    }

    @Transactional
    @Modifying
    public MessageDTO saveMessage(TextMessageDTO textMessageDTO) {
        Message message = new Message();
        message.setTypeMessage(textMessageDTO.getTypeMessage());
        message.setIsRead(Constant.STATUS.NO);
        message.setIsDeleted(Constant.STATUS.NO);
//        message.setTimeSend(new Timestamp(new Date().getTime()));
        message.setUrl("");
        message.setTimeCall((double) 0);
        message.setContent(textMessageDTO.getContent());
        message.setTypeTime("");
//        Conversation conversation = conversationService.getConversationById(textMessageDTO.getConversationId());
//        if (conversation == null) {
//            conversation = new Conversation();
//            conversation.setName("da");
//            conversation.setCreatedTime(new Timestamp(new Date().getTime()));
//            conversationService.add(conversation);
//            message.setConversation(conversation);


            MemberConversation memberConversationSender = new MemberConversation();
//            memberConversationSender.setConversation(conversation);
            memberConversationSender.setIsDeleted(Constant.STATUS.NO);
            memberConversationSender.setIsCreateConversation(Constant.STATUS.YES);
            memberConversationSender.setTimeJoinConversation(new Timestamp(new Date().getTime()));
            memberConversationSender.setTimeJoinConversation(new Timestamp(new Date().getTime()));
//            User userCreateConversation = iUserRepositoryJPA.findUsersById(textMessageDTO.getSenderId()).get();
//            memberConversationSender.setMemberConversation(userCreateConversation);
            memberConversationRepository.add(memberConversationSender);


            MemberConversation memberConversationReceiver = new MemberConversation();
//            memberConversationReceiver.setConversation(conversation);
            memberConversationReceiver.setIsDeleted(Constant.STATUS.NO);
            memberConversationReceiver.setIsCreateConversation(Constant.STATUS.YES);
            memberConversationReceiver.setTimeJoinConversation(new Timestamp(new Date().getTime()));
            memberConversationReceiver.setTimeJoinConversation(new Timestamp(new Date().getTime()));
//            User userConversationReceiver = iUserRepositoryJPA.findUsersById(textMessageDTO.getSenderId()).get();
//            memberConversationReceiver.setMemberConversation(userConversationReceiver);
            memberConversationRepository.add(memberConversationReceiver);
//
//
//            message.setConversation(conversation);
//            message.setMemberConversation(memberConversationSender);
//        } else {
//            MemberConversation memberConversation = memberConversationRepository.getByUserId(textMessageDTO.getSenderId(), conversation.getId());
//            message.setConversation(conversation);
//            message.setMemberConversation(memberConversation);
//        }
//        messageRepository.add(message);
        return null;
//                .idMessage(message.getId())
//                .idSender(message.getMemberConversation().getId())
//                .timeSend(message.getTimeSend())
//                .url(message.getUrl())
//                .typeMessage(message.getTypeMessage())
//                .content(message.getContent())
//                .timeCall(message.getTimeCall())
//                .typeTime(message.getTypeTime())
//                .conversationId(message.getConversation().getId()).build();

    }
    @Transactional
    @Modifying
    public MessageDTO saveMediaMessage(MediaMessageDTO mediaMessageDTO) {
        String url="https://lh3.google.com/u/0/d/"+googleDriveFile.uploadFile(mediaMessageDTO.getMediaFile(),"Root",true);
        Message message = new Message();
        message.setTypeMessage(mediaMessageDTO.getTypeMessage());
        message.setIsRead(Constant.STATUS.NO);
        message.setIsDeleted(Constant.STATUS.NO);
//        message.setTimeSend(new Timestamp(new Date().getTime()));
        message.setUrl(url);
        message.setTimeCall((double) 0);
        message.setContent("");
        message.setTypeTime("");
        Conversation conversation = conversationService.getConversationById(mediaMessageDTO.getConversationId());
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setName("da");
            conversation.setCreatedTime(new Timestamp(new Date().getTime()));
            conversationService.add(conversation);
//            message.setConversation(conversation);


            MemberConversation memberConversationSender = new MemberConversation();
//            memberConversationSender.setConversation(conversation);
            memberConversationSender.setIsDeleted(Constant.STATUS.NO);
            memberConversationSender.setIsCreateConversation(Constant.STATUS.YES);
            memberConversationSender.setTimeJoinConversation(new Timestamp(new Date().getTime()));
            memberConversationSender.setTimeJoinConversation(new Timestamp(new Date().getTime()));
//            User userCreateConversation = iUserRepositoryJPA.findUsersById(mediaMessageDTO.getSenderId()).get();
//            memberConversationSender.setMemberConversation(userCreateConversation);
            memberConversationRepository.add(memberConversationSender);


            MemberConversation memberConversationReceiver = new MemberConversation();
//            memberConversationReceiver.setConversation(conversation);
            memberConversationReceiver.setIsDeleted(Constant.STATUS.NO);
            memberConversationReceiver.setIsCreateConversation(Constant.STATUS.YES);
            memberConversationReceiver.setTimeJoinConversation(new Timestamp(new Date().getTime()));
            memberConversationReceiver.setTimeJoinConversation(new Timestamp(new Date().getTime()));
//            User userConversationReceiver = iUserRepositoryJPA.findUsersById(mediaMessageDTO.getSenderId()).get();
//            memberConversationReceiver.setMemberConversation(userConversationReceiver);
            memberConversationRepository.add(memberConversationReceiver);


//            message.setConversation(conversation);
//            message.setMemberConversation(memberConversationSender);
        } else {
//            MemberConversation memberConversation = memberConversationRepository.getByUserId(mediaMessageDTO.getSenderId(), conversation.getId());
//            message.setConversation(conversation);
//            message.setMemberConversation(memberConversation);
        }
//        messageRepository.add(message);
//        return MessageDTO.builder()
//                .idMessage(message.getId())
//                .idSender(message.getMemberConversation().getId())
//                .timeSend(message.getTimeSend())
//                .url(message.getUrl())
//                .typeMessage(message.getTypeMessage())
//                .content(message.getContent())
//                .timeCall(message.getTimeCall())
//                .typeTime(message.getTypeTime())
//                .conversationId(message.getConversation().getId()).build();
        return null;

    }
}
