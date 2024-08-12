package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import wint.webchat.entities.conversation.Conversation;
import wint.webchat.modelDTO.reponse.ConversationMessageDTO;
import wint.webchat.repositories.IConversationRepository;

import java.util.List;

@Repository
public class ConversationRepositoryImpl implements IConversationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<String> add(Conversation conversation) {
        entityManager.persist(conversation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(Conversation conversation) {
        return null;
    }

    @Override
    public List<Object[]> getList(Long id, int startGetter, int amountGet) {
        return null;
    }

    @Override
    public List<ConversationMessageDTO> getListConversation(Long id, int startGetter, int amountGet) {
//        String sql = """
//                    select
//                        u.id, u.fullName, u.isOnline, u.urlAvatar,
//                        c.id, mc.id,
//                        (select m.id from Message m
//                         where m.conversation.id = c.id
//                         order by m.timeSend desc
//                         fetch first 1 row only) as messageId,
//                        (select m.memberConversation.id from Message m
//                         where m.conversation.id = c.id
//                         order by m.timeSend desc
//                         fetch first 1 row only) as memberConversationId,
//                        (select m.typeMessage from Message m
//                         where m.conversation.id = c.id
//                         order by m.timeSend desc
//                         fetch first 1 row only) as typeMessage,
//                        (select m.timeSend from Message m
//                         where m.conversation.id = c.id
//                         order by m.timeSend desc
//                         fetch first 1 row only) as timeSend
//                    from Conversation c
//                    inner join MemberConversation mc on c.id = mc.conversation.id
//                    inner join User u on u.id = mc.memberConversation.id
//                    where c.id in (
//                        select mc.conversation.id from MemberConversation mc
//                        where mc.memberConversation.id = :userId
//                    )
//                    and mc.memberConversation.id <> :userId
//                """;
        Query query = entityManager.createQuery("", ConversationMessageDTO.class);
        query.setParameter("userId", id);
        query.setFirstResult(startGetter);
        query.setMaxResults(amountGet);
        return query.getResultList();
    }

    @Override
    public Conversation getConversationById(Long id) {
        return entityManager.find(Conversation.class, id);
    }
}
