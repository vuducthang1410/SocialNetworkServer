package wint.webchat.service.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl {

    private final JavaMailSender javaMailSender;
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@example.com");
        message.setTo(to);
        message.setSubject("[HYGGE]Reset your password!!");
        message.setText(body);
        javaMailSender.send(message);
    }
    public void sendHtmlMailResetPassword(String addressEmailReceiver,String url){
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true,"UTF-8");
            helper.setFrom("Hygge");
            helper.setTo(addressEmailReceiver);
            helper.setSubject("[HYGGE]Reset your password!!");
            helper.setText(getMailTemplate(url),true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private String getMailTemplate(String urlResetPassword){
        String template="<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      background-color: #f4f4f4;\n" +
                "    }\n" +
                "\n" +
                "    .container {\n" +
                "      width: 100%;\n" +
                "      max-width: 600px;\n" +
                "      margin: 0 auto;\n" +
                "      background-color: #ffffff;\n" +
                "      border: 1px solid #dddddd;\n" +
                "      padding: 15px;\n" +
                "    }\n" +
                "    .header h1 {\n" +
                "      margin: 0;\n" +
                "      font-size: 24px;\n" +
                "    }\n" +
                "\n" +
                "    .content {\n" +
                "      padding: 15px;\n" +
                "    }\n" +
                "\n" +
                "    .content h2 {\n" +
                "      color: #333333;\n" +
                "      font-size: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .content p {\n" +
                "      color: #555555;\n" +
                "      font-size: 16px;\n" +
                "      line-height: 1.5;\n" +
                "    }\n" +
                "\n" +
                "    .content a {\n" +
                "      color: #007bff;\n" +
                "      text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                "    .footer {\n" +
                "      text-align: center;\n" +
                "      padding: 5px 0;\n" +
                "      color: #888888;\n" +
                "      font-size: 14px;\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (max-width: 600px) {\n" +
                "      .container {\n" +
                "        width: 100%;\n" +
                "        padding: 15px;\n" +
                "      }\n" +
                "\n" +
                "      .header h1 {\n" +
                "        font-size: 20px;\n" +
                "      }\n" +
                "\n" +
                "      .content h2 {\n" +
                "        font-size: 18px;\n" +
                "      }\n" +
                "\n" +
                "      .content p {\n" +
                "        font-size: 14px;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <header>\n" +
                "    <h2 style=\"text-align: center;\">Reset your Hygge password </h2>\n" +
                "  </header>\n" +
                "  <div class=\"container\">\n" +
                "    <div class=\"content\">\n" +
                "      <h2 style=\"text-align: center;\">Hygge password reset</h2>\n" +
                "      <p>We heard that you lost your Hyyge password. Sorry about that!</p>\n" +
                "      <p>\n" +
                "        But don’t worry! You can use the following button to reset your password:\n" +
                "      </p>\n" +
                "      <div style=\"display: flex;justify-content: center;\">\n" +
                "        <button style=\"background-color: rgb(5, 135, 38); height: 4em; width: 15em;border: none;border-radius: 0.7em;\"> <a\n" +
                "          href=\""+urlResetPassword+"\" style=\"color: white;font-size: 16px;font-weight: 500;\">Reset your password</a>.</button>\n" +
                "      </div>\n" +
                "      \n" +
                "      <p>If you don’t use this link within 3 hours, it will expire.\n" +
                "        To get a new password reset link, visit:<a href=\"http://localhost:3000/forget-password\"> new link</a> </p>\n" +
                "      <p> Thanks\n" +
                "      </p>\n" +
                "      <p>From: Vu Duc Thang</p>\n" +
                "    </div>\n" +
                "\n" +
                "  </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        return template;
    }
}
