package wint.webchat.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.entities.user.Friend;
import wint.webchat.mapper.JsonMapper;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.FriendDTO;
import wint.webchat.repositories.IFriendRepository;
import wint.webchat.repositories.IUserRepositoryJPA;
import wint.webchat.service.IFriendService;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements IFriendService {
    private final IFriendRepository friendRepository;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final JsonMapper mapper;

    @Override
    public ApiResponse<List<FriendDTO>> getListFriendById(String id, int startGet, int amount) {
        return buildFriendApiResponse(() -> friendRepository.getListFriendById(id, startGet * amount, amount), amount);
    }
    @Override
    public ApiResponse<List<FriendDTO>> getInvitationsReceivedById(String id, int start, int amount) {
        return buildFriendApiResponse(() -> friendRepository.getInvitationsReceivedById(id, start * amount, amount), amount);
    }

    @Override
    public ApiResponse<List<FriendDTO>> getInvitationsSentById(String id, int start, int amount) {
        return buildFriendApiResponse(() -> friendRepository.getInvitationsSentById(id, start * amount, amount), amount);
    }

    //          accept	refuse	delete
//    friend	true	false	false
//    sender	false	false	false
//    refuse	false	true	false
//    delete 	false	false	true
    @Modifying
    @Transactional
    @Override
    public ApiResponse<String> sendFriendInvitation(String senderId, String receiverId) {
        var friendList = friendRepository.getFriendById(senderId, receiverId);
        if (!friendList.isEmpty()) {
            Friend friend = friendList.stream().findFirst().get();
            if (friend.getIsAccept()) return ApiResponse.<String>builder()
                    .code(200)
                    .error(Map.of("Refuse", "already friends"))
                    .build();

            if (friend.getIsRefuse()) {
                //if the recipient has never declined the sender's invitation
                //if condition = false ,send invitation to receiver else return
//                if (!Objects.equals(friend.getUserInvitationSender().getId(), senderId)) {
//                    friend.setTimeSend(new Timestamp(new Date().getTime()));
//                    friend.setIsAccept(false);
//                    friend.setIsRefuse(false);
//                    friend.setIsDelete(false);
//                    User sender = friend.getUserInvitationReceiver();
//                    friend.setUserInvitationReceiver(friend.getUserInvitationSender());
//                    friend.setUserInvitationSender(sender);
//                    friendRepository.add(friend);
//                    return ApiResponse.<String>builder()
//                            .message("Successfully")
//                            .success(true)
//                            .code(200)
//                            .error(Collections.emptyMap())
//                            .build();
                } else {
                    return ApiResponse.<String>builder()
                            .code(200)
                            .error(Map.of("Refuse", "already friends"))
                            .build();
                }
            } else {
//                if friend invitation not delele , friend invitation is exist
//                if (friend.getIsDelete()) {
//                    User user1 = friend.getUserInvitationReceiver();
//                    User user2 = friend.getUserInvitationSender();
//                    friend.setIsAccept(false);
//                    friend.setIsRefuse(false);
//                    friend.setIsDelete(false);
//                    friend.setTimeSend(new Timestamp(new Date().getTime()));
//                    boolean user1IsSender = user1.getId().equals(senderId);
//                    if (user1IsSender) {
//                        friend.setUserInvitationSender(user1);
//                        friend.setUserInvitationReceiver(user2);
//                    } else {
//                        friend.setUserInvitationSender(user2);
//                        friend.setUserInvitationReceiver(user1);
//                    }
//                    friendRepository.add(friend);
//                    return ApiResponse.<String>builder()
//                            .message("Successfully")
//                            .success(true)
//                            .code(200)
//                            .error(Collections.emptyMap())
//                            .build();
//                } else {
//                    return ApiResponse.<String>builder()
//                            .message("The friend request already exists")
//                            .success(false)
//                            .code(200)
//                            .error(Map.of("Refuse", "The friend request already exists"))
//                            .build();
//                }
            }
//        }
        var userSender = userRepositoryJPA.findUsersById(senderId);
        var userReceiver = userRepositoryJPA.findUsersById(receiverId);
        if (userSender.isPresent() && userReceiver.isPresent()) {
            Friend friend = new Friend();
            friend.setIsDelete(false);
//            friend.setUserInvitationReceiver(userReceiver.get());
//            friend.setUserInvitationSender(userSender.get());
//            friend.setTimeSend(new Timestamp(new Date().getTime()));
            friend.setIsAccept(false);
            friend.setIsRefuse(false);
            friendRepository.add(friend);
            return ApiResponse.<String>builder()
                    .code(200)
                    .error(Collections.emptyMap())
                    .build();
        }
        return ApiResponse.<String>builder()
                .code(200)
                .error(Map.of("Failure", "not found user"))
                .build();
    }

    @Override
    @Transactional
    @Modifying
    public ApiResponse<String> deleteFriendRelationship(String userId1, String userId2) {
        var friendList = friendRepository.getFriendById(userId1, userId2);
        if (!friendList.isEmpty()) {
            Friend friend = friendList.stream().findFirst().get();
            friend.setIsDelete(true);
            friend.setIsAccept(false);
            friend.setIsRefuse(false);
//            friend.setTimeSend(new Timestamp(new Date().getTime()));
            friendRepository.update(friend);
            return ApiResponse.<String>builder()
                    .code(200)
                    .error(Collections.emptyMap())
                    .build();
        }
        return ApiResponse.<String>builder()
                .code(200)
                .error(Map.of("Failure", "not found friend"))
                .build();
    }

    @Override
    @Transactional
    @Modifying
    public ApiResponse<String> deleteInvitations(String senderId, String receiverId) {
        var friendList = friendRepository.getFriendById(senderId, receiverId);
        if (!friendList.isEmpty()) {
            Friend friend = friendList.stream().findFirst().get();
//            if (friend.getUserInvitationSender().getId().equals(senderId)) {
//                friend.setIsDelete(true);
//                friend.setIsRefuse(false);
//            } else {
//                friend.setIsDelete(false);
//                friend.setIsRefuse(true);
//            }
            friend.setIsAccept(false);
//            friend.setTimeSend(new Timestamp(new Date().getTime()));
            friendRepository.update(friend);
            return ApiResponse.<String>builder()
                    .code(200)
                    .error(Collections.emptyMap())
                    .build();
        }
        return ApiResponse.<String>builder()
                .code(200)
                .error(Map.of("Failure", "not found invitation friend"))
                .build();
    }

    @Override
    @Transactional
    @Modifying
    public ApiResponse<String> acceptInvitationFriend(String senderId, String receiverId) {
        var friendList = friendRepository.getFriendById(senderId, receiverId);
        if (!friendList.isEmpty()) {
            Friend friend = friendList.stream().findFirst().get();
            friend.setIsDelete(false);
            friend.setIsAccept(true);
            friend.setIsRefuse(false);
//            friend.setTimeSend(new Timestamp(new Date().getTime()));
            friendRepository.update(friend);
            return ApiResponse.<String>builder()
                    .code(200)
                    .error(Collections.emptyMap())
                    .build();
        }
        return ApiResponse.<String>builder()
                .code(200)
                .error(Map.of("Failure", "not found invitation friend"))
                .build();
    }

    private ApiResponse<List<FriendDTO>> buildFriendApiResponse(Supplier<List<FriendDTO>> friendListSupplier, int amount) {
        List<FriendDTO> listFriend = friendListSupplier.get();
        if (listFriend.isEmpty() || listFriend.size() < amount) {
            return ApiResponse.<List<FriendDTO>>builder()
                    .data(listFriend)
                    .code(HttpStatus.NO_CONTENT.value())
                    .error(Collections.emptyMap())
                    .build();
        } else {
            return ApiResponse.<List<FriendDTO>>builder()
                    .data(listFriend)
                    .code(HttpStatus.OK.value())
                    .error(Collections.emptyMap())
                    .build();
        }
    }
    private ApiResponse<List<String>> buildFriendTest(Supplier<List<FriendDTO>> friendListSupplier, int amount) {
        List<FriendDTO> listFriend = friendListSupplier.get();
        List<String> list=listFriend.stream().map(e-> {
            try {
                return mapper.objectToJson(e);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
        if (listFriend.isEmpty() || listFriend.size() < amount) {
            return ApiResponse.<List<String>>builder()
                    .data(list)
                    .code(HttpStatus.NO_CONTENT.value())
                    .error(Collections.emptyMap())
                    .build();
        } else {
            return ApiResponse.<List<String>>builder()
                    .data(list)
                    .code(HttpStatus.OK.value())
                    .error(Collections.emptyMap())
                    .build();
        }
    }
}
//    @Override
//    @Modifying
//    @Transactional
//    public ApiResponse<String> sendFriendInvitation(Long senderId, Long receiverId) {
//        var friendList = friendRepository.getFriendById(senderId, receiverId);
//
//        if (friendList.isEmpty()) {
//            return handleNewFriendInvitation(senderId, receiverId);
//        }
//
//        Friend friend = friendList.stream().findFirst().get();
//
//        if (friend.getIsAccept()) {
//            return buildErrorResponse("You are already friends", "already friends");
//        }
//
//        if (friend.getIsRefuse()) {
//            return handleRefusedInvitation(senderId, friend);
//        }
//
//        if (friend.getIsDelete()) {
//            return handleDeletedInvitation(senderId, friend);
//        }
//
//        return buildErrorResponse("The friend request already exists", "The friend request already exists");
//    }
//
//    private ApiResponse<String> handleNewFriendInvitation(Long senderId, Long receiverId) {
//        var userSender = userRepositoryJPA.findUsersById(senderId);
//        var userReceiver = userRepositoryJPA.findUsersById(receiverId);
//
//        if (userSender.isPresent() && userReceiver.isPresent()) {
//            Friend friend = new Friend();
//            friend.setIsDelete(false);
//            friend.setUserInvitationReceiver(userReceiver.get());
//            friend.setUserInvitationSender(userSender.get());
//            friend.setTimeSend(new Timestamp(new Date().getTime()));
//            friend.setIsAccept(false);
//            friend.setIsRefuse(false);
//            friendRepository.add(friend);
//            return buildSuccessResponse("Successfully");
//        }
//
//        return buildErrorResponse("Not found user", "not found user");
//    }
//
//    private ApiResponse<String> handleRefusedInvitation(Long senderId, Friend friend) {
//        if (!Objects.equals(friend.getUserInvitationSender().getId(), senderId)) {
//            resetFriendInvitation(friend);
//            swapSenderAndReceiver(friend);
//            friendRepository.add(friend);
//            return buildSuccessResponse("Successfully");
//        }
//
//        return buildErrorResponse("You have been rejected so you cannot repeat that action", "already friends");
//    }
//
//    private ApiResponse<String> handleDeletedInvitation(Long senderId, Friend friend) {
//        resetFriendInvitation(friend);
//        assignSenderAndReceiver(senderId, friend);
//        friendRepository.add(friend);
//        return buildSuccessResponse("Successfully");
//    }
//
//    private void resetFriendInvitation(Friend friend) {
//        friend.setTimeSend(new Timestamp(new Date().getTime()));
//        friend.setIsAccept(false);
//        friend.setIsRefuse(false);
//        friend.setIsDelete(false);
//    }
//
//    private void swapSenderAndReceiver(Friend friend) {
//        User sender = friend.getUserInvitationReceiver();
//        friend.setUserInvitationReceiver(friend.getUserInvitationSender());
//        friend.setUserInvitationSender(sender);
//    }
//
//    private void assignSenderAndReceiver(Long senderId, Friend friend) {
//        User user1 = friend.getUserInvitationReceiver();
//        User user2 = friend.getUserInvitationSender();
//        boolean user1IsSender = user1.getId().equals(senderId);
//        if (user1IsSender) {
//            friend.setUserInvitationSender(user1);
//            friend.setUserInvitationReceiver(user2);
//        } else {
//            friend.setUserInvitationSender(user2);
//            friend.setUserInvitationReceiver(user1);
//        }
//    }
//
//    private ApiResponse<String> buildSuccessResponse(String message) {
//        return ApiResponse.<String>builder()
//                .message(message)
//                .success(true)
//                .code(200)
//                .error(Collections.emptyMap())
//                .build();
//    }
//
//    private ApiResponse<String> buildErrorResponse(String message, String error) {
//        return ApiResponse.<String>builder()
//                .message(message)
//                .success(false)
//                .code(200)
//                .error(Map.of("Error", error))
//                .build();
//    }
//
//    @Override
//    @Transactional
//    @Modifying
//    public ApiResponse<String> deleteFriendRelationship(Long userId1, Long userId2) {
//        var friendList = friendRepository.getFriendById(userId1, userId2);
//        if (!friendList.isEmpty()) {
//            Friend friend = friendList.stream().findFirst().get();
//            friend.setIsDelete(true);
//            friend.setIsAccept(false);
//            friend.setIsRefuse(false);
//            friend.setTimeSend(new Timestamp(new Date().getTime()));
//            friendRepository.update(friend);
//            return buildSuccessResponse("Successfully");
//        }
//        return buildErrorResponse("Not found friend", "not found friend");
//    }
//
//    @Override
//    public ApiResponse<String> deleteInvitations(Long senderId, Long receiverId) {
//        var friendList = friendRepository.getFriendById(senderId, receiverId);
//        if (!friendList.isEmpty()) {
//            Friend friend = friendList.stream().findFirst().get();
//            if (friend.getUserInvitationSender().getId().equals(senderId)) {
//                friend.setIsDelete(true);
//                friend.setIsRefuse(false);
//            } else {
//                friend.setIsDelete(false);
//                friend.setIsRefuse(true);
//            }
//            friend.setIsAccept(false);
//            friend.setTimeSend(new Timestamp(new Date().getTime()));
//            friendRepository.update(friend);
//            return buildSuccessResponse("Successfully");
//        }
//        return buildErrorResponse("Not found friend", "not found invitation friend");
//    }
//
//    @Override
//    public ApiResponse<String> acceptInvitationFriend(Long senderId, Long receiverId) {
//        var friendList = friendRepository.getFriendById(senderId, receiverId);
//        if (!friendList.isEmpty()) {
//            Friend friend = friendList.stream().findFirst().get();
//            friend.setIsDelete(false);
//            friend.setIsAccept(true);
//            friend.setIsRefuse(false);
//            friend.setTimeSend(new Timestamp(new Date().getTime()));
//            friendRepository.update(friend);
//            return buildSuccessResponse("Successfully");
//        }
//        return buildErrorResponse("Not found friend", "not found invitation friend");
//    }

