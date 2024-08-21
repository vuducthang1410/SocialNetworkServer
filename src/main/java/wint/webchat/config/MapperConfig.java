package wint.webchat.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import wint.webchat.entities.user.User;
import wint.webchat.modelDTO.reponse.AuthResponseData;
//import wint.webchat.security.CustomUserDetail;

@Component
@RequiredArgsConstructor
public class MapperConfig {
    private final ModelMapper modelMapper;

//    public void mapToAuthResponseData(){
//        PropertyMap<User,AuthResponseData> propertyMap=new PropertyMap<User, AuthResponseData>() {
//            @Override
//            protected void configure() {
//                map().setUserId(source.getId());
//            }
//        };
//        modelMapper.addMappings(propertyMap);
//    }
}
