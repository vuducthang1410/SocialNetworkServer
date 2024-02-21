package wint.webchat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import wint.webchat.entities.user.Role;
import wint.webchat.service.Impl.RoleService;

@SpringBootApplication
public class WebChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebChatApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RoleService roleService) {
        return args -> {
            roleService.addRole(new Role(null,"ROLE_USER"));
            roleService.addRole(new Role(null,"ROLE_ADMIN"));
            roleService.addRole(new Role(null,"ROLE_MANAGER"));
            roleService.addRole(new Role(null,"ROLE_SUPER_ADMIN"));
        };
    }
}
