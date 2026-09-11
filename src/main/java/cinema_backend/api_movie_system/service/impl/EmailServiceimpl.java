// package cinema_backend.api_movie_system.service.impl;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// @Service
// public class EmailServiceimpl implements cinema_backend.api_movie_system.service.EmailService {
//     private final JavaMailSender mailSender;

//     @Value("${app.frontend-url}")
//     private String frontendUrl;

//     public EmailServiceimpl(JavaMailSender mailSender) {
//         this.mailSender = mailSender;
//     }

//     @Override
//     public void sendOnboardingEmail(String recipientEmail, String token) {
//         String setupUrl = frontendUrl + "/set-password?token=" + token;

//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setTo(recipientEmail);
//         message.setSubject("Set Up Your Account Password");
//         message.setText("Welcome to the team!\n\n" +
//                 "Please click the link below to set your account password:\n" + setupUrl);

//         mailSender.send(message);
//     }
// }
