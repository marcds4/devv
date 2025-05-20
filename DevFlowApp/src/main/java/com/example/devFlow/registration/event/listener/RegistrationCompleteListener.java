package com.example.devFlow.registration.event.listener;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.devFlow.registration.event.RegistrationCompleteEvent;
import com.example.devFlow.user.User;
import com.example.devFlow.user.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;


@Component
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {
	private final UserService userService;
	private final JavaMailSender mailSender;
	private User theUser;
    private static final Logger logger = LoggerFactory.getLogger(RegistrationCompleteListener.class);

    
    public RegistrationCompleteListener(UserService userService,JavaMailSender mailSender) {
		super();
		this.userService = userService;
		this.mailSender = mailSender;
	}


	@Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        theUser = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userService.saveUserVerificationToken(theUser,verificationToken);
        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;
        try {
			sendVerificationEmail(url);
		} catch (UnsupportedEncodingException | MessagingException e) {
			throw new RuntimeException(e);
		}
        logger.info("Click the link to verify your registration: {}", url);
    }
	
	public void sendVerificationEmail(String url) throws UnsupportedEncodingException, MessagingException {
		String subject = "Email Verification";
		String senderName="User Registration Portal Service";
		String mailContent="Thank you for registering with us,"+""+"Please, follow the link below to complete your registration.<p>"+"<a href=\""+url+"\">Verify your email to activate your account</a>"+"<p> Thank you <br> Users Registration Portal Service";
		MimeMessage message=mailSender.createMimeMessage();
		var messageHelper=new MimeMessageHelper(message);
		messageHelper.setFrom("mariiasthh@gmail.com",senderName);
		messageHelper.setTo(theUser.getEmail());
		messageHelper.setSubject(subject);
		messageHelper.setText(mailContent,true);
		mailSender.send(message);
	}
}

