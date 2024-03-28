package wint.webchat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wint.webchat.mapper.MapperObj;
import wint.webchat.modelDTO.PostDTO;
import wint.webchat.repositories.IPostRepository;
import wint.webchat.service.IPostService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final MapperObj mapperObj;

    public PostServiceImpl(@Autowired IPostRepository postRepository,
                           @Autowired MapperObj mapperObj) {
        this.postRepository = postRepository;
        this.mapperObj=mapperObj;
    }

    @Override
    public List<PostDTO> getListPost(Long id, int startGetter, int amountGet) {
        return postRepository.getList(id,startGetter,amountGet)
                .stream()
                .map(mapperObj::getPostDTO)
                .collect(Collectors.toList());
    }
}
