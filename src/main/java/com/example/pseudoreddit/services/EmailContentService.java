package com.example.pseudoreddit.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@AllArgsConstructor
public class EmailContentService {

    private final TemplateEngine templateEngine;



    String buildEmailContent(String message){
        Context context = new Context();

        context.setVariable("message", message);
        return templateEngine.process("mailMessage", context);

    }




}
