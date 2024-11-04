package wint.webchat.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import wint.webchat.entities.post.Post;
import wint.webchat.repositories.IPostRepository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public ResponseEntity<String> add(Post post) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(Post post) {
        return null;
    }

    @Override
    public List<Object[]> getList(String id, int startGetter, int amountGet) {
        return null;
    }

//    @Override
//    public List<Object[]> getList(Long id,int startGetter,int amountGet) {
//        StoredProcedureQuery procedureQuery=entityManager.createStoredProcedureQuery("get_post_in_profile");
//        procedureQuery.registerStoredProcedureParameter("user_id", Long.class, ParameterMode.IN);
//        procedureQuery.registerStoredProcedureParameter("start_getter", Long.class,ParameterMode.IN);
//        procedureQuery.registerStoredProcedureParameter("amount_post_get", Long.class,ParameterMode.IN);
//        procedureQuery.setParameter("user_id",id);
//        procedureQuery.setParameter("start_getter",startGetter);
//        procedureQuery.setParameter("amount_post_get",amountGet);
//        return procedureQuery.getResultList();
//    }
}
