package com.example.pseudoreddit.services;


import com.example.pseudoreddit.exceptions.EmailException;
import com.example.pseudoreddit.models.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailContentService emailContentService;


    public void sendMail(NotificationEmail notificationEmail){

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("pseudoreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(emailContentService.buildEmailContent(notificationEmail.getBody()));

        };

        try {
            mailSender.send(messagePreparator);
            log.info("Email sent");

        } catch (MailException e){
            log.info(e.getMessage());
            throw new EmailException("Email sending exception occured");
        }



    }





}
