//package wint.webchat.UserRepository;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import wint.webchat.entities.user.User;
//import wint.webchat.repositories.IUserRepositoryJPA;
//import wint.webchat.repositories.IUserRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Configuration
//@ComponentScan(basePackages = "wint.webchat.repositories.Impl")
//public class TestCase1 {
//
//    @MockBean
//    private IUserRepository userRepository;
//
//    @Autowired
//    private IUserRepositoryJPA userRepositoryImpl;
//
//    @Before
//    public void setup() {
//        // Mock behavior of UserRepository
//        Mockito.when(userRepository.add(Mockito.any(User.class)))
//                .thenReturn(ResponseEntity.status(HttpStatus.OK).build());
//
//        // Additional setup if needed
//    }
//
//    @Test
//    public void testAddUser() {
//        // Arrange
//        User user = new User();
//        user.setUserName("vuducthanghehe");
//
//        // Act
//        ResponseEntity<String> result = userRepositoryImpl.add(user);
//
//        // Assert
//        Assert.assertEquals(HttpStatus.OK, result);
//        Mockito.verify(userRepository, Mockito.times(1)).add(Mockito.any(User.class));
//    }
//}
