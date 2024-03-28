package wint.webchat.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import wint.webchat.entities.user.Role;
import wint.webchat.repositories.IRoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final JavaMailSenderImpl mailSender;
    private final IRoleRepository roleRepository;

    public void addRole(Role role) {
        roleRepository.save(role);
    }
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
