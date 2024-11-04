package wint.webchat.repositories;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface BaseMethod <T>{
    ResponseEntity<String> add(T t);
    ResponseEntity<String> delete(String id);
    ResponseEntity<String> update(T t);
    List<Object[]> getList(String id,int startGetter,int amountGet);
}
