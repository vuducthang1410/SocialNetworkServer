package wint.webchat.controller.user;

import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wint.webchat.modelDTO.request.VideoCallDTO;
import wint.webchat.repositories.Impl.MemberConversationRepository;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VideoCallController {
    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;
    private final SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    @PostMapping("/api/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

//    @MessageMapping("/video-call")
//    public void sendRequestCall(@Payload VideoCallDTO videoCallDTO) {
//        Long userIdReceiveiver = memberConversationRepository.getUserIdByIdMemberConversationId(videoCallDTO.getReceiverid());
//        messagingTemplate.convertAndSendToUser(userIdReceiveiver.toString(), "/accept-video-call", videoCallDTO);
//    }
//    @MessageMapping("/accept-call")
//    public void sendAccept(@Payload VideoCallDTO videoCallDTO){
//        messagingTemplate.convertAndSendToUser(videoCallDTO.getSenderId().toString(),"reply-request",videoCallDTO);
//    }
}
