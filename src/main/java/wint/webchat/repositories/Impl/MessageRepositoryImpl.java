package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import wint.webchat.entities.conversation.Conversation;
import wint.webchat.entities.conversation.Message;
import wint.webchat.modelDTO.reponse.MessageDTO;
import wint.webchat.repositories.IMessageRepository;

import java.util.List;

@Repository
public class MessageRepositoryImpl implements IMessageRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<String> add(Message message) {
        entityManager.persist(message);
        return null;
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(Message message) {
        return null;
    }

    @Override
    public List<Object[]> getList(Long id, int startGetter, int amountGet) {
        return null;
    }
    @Override
    public List<MessageDTO>  getListMessageByConversationId(int amount,Long conversationId,int start){
//        String sql= """
//                    select
//                    m.id,
//                    m.memberConversation.id,
//                    m.timeSend,
//                    m.typeMessage,
//                    m.content,
//                    m.url,
//                    m.typeTime,
//                    m.timeCall,
//                    c.id
//                     from Conversation c inner join Message  m on m.conversation.id=c.id
//                    inner join MemberConversation mc on mc.id=m.memberConversation.id
//                    where m.conversation.id=:conversationId and m.isDelete =false
//                    order by m.timeSend desc
//                """;
        Query query=entityManager.createQuery("", MessageDTO.class);
        query.setParameter("conversationId",conversationId);
        query.setMaxResults(amount);
        query.setFirstResult(start);
        return query.getResultList();
    }
}
