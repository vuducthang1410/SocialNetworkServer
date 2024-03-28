package wint.webchat.service;

import wint.webchat.modelDTO.PostDTO;

import java.util.List;

public interface IPostService {
    List<PostDTO> getListPost(Long id,int startGetter,int amountGet);
}
