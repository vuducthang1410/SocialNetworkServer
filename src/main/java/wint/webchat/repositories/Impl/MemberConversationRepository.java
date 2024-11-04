package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import wint.webchat.entities.conversation.MemberConversation;
import wint.webchat.repositories.BaseMethod;

import java.util.List;

@Repository
public class MemberConversationRepository implements BaseMethod<MemberConversation> {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public ResponseEntity<String> add(MemberConversation memberConversation) {
        entityManager.persist(memberConversation);
        return ResponseEntity.ok("");
    }
    public MemberConversation getById(String id){
      return  entityManager.find(MemberConversation.class,id);
    }
    public MemberConversation getByUserId(String userId,String conversationId){
        String sql= """
                select mc from MemberConversation mc inner join User u on mc.id=u.id
                where u.id=:userId and mc.id=:conversationId
                """;
        Query query=entityManager.createQuery(sql,MemberConversation.class);
        query.setParameter("userId",userId);
        query.setParameter("conversationId",conversationId);
        List<MemberConversation> result=query.getResultList();
        return  result.stream().findFirst().get();
    }
    public String getUserIdByIdMemberConversationId(String id){
        String sql= """
                SELECT u.id from MemberConversation ms inner join User  u on u.id=ms.id
                where ms.id=:id
                """;
        Query query=entityManager.createQuery(sql,Long.class);
        query.setParameter("id",id);
        List<String> result=query.getResultList();
        return  result.size()==0?null:result.stream().findFirst().get();
    }

    @Override
    public ResponseEntity<String> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(MemberConversation memberConversation) {
        return null;
    }

    @Override
    public List<Object[]> getList(String id, int startGetter, int amountGet) {
        return null;
    }
}
