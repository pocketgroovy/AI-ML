/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package com.cs6440.abc.STDI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Locale;

@Service
public class EmailService {

    private static final String EMAIL_HTML_TEMPLATE_NAME = "mail/html/email-simple";
    private static final String EMAIL_HTML_TEMPLATE_NAME_DOCTOR = "mail/html/doctor-email-simple";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JavaMailSender mailSender;

    private TemplateEngine htmlTemplateEngine;

    @Autowired
    public EmailService(TemplateEngine templateEngine) {
        this.htmlTemplateEngine = templateEngine;
    }


    /* 
     * Send HTML mail (simple) 
     */
    public void sendHTMLMail(final String appEmail,
        final String senderName, final String senderEmail, final String recipientEmail, final String appointmentDate,
        final String practitionerName,  final String comment, final Locale locale) throws MessagingException {
        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", senderName);
        ctx.setVariable("senderEmail", senderEmail);
        ctx.setVariable("requestDate", new Date());
        ctx.setVariable("recipientEmail", recipientEmail);
        ctx.setVariable("appointmentDate", appointmentDate);
        ctx.setVariable("practitionerName", practitionerName);
        ctx.setVariable("fareaNl2br", comment);

        sendToUser(ctx, appEmail, senderEmail);
        sendToPractitioner(ctx, appEmail, recipientEmail);
    }

    private void sendToUser(final Context ctx, final String appEmail,final String senderEmail) throws MessagingException {

        // Prepare message sent to User
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("An Appointment Request Sent From STDI");
        message.setFrom(appEmail);
        message.setTo(senderEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_HTML_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Send email
        this.mailSender.send(mimeMessage);
    }

    private void sendToPractitioner(final Context ctx, final String appEmail,final String recipientEmail) throws MessagingException {

        // Prepare message sent to User
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("An Appointment Request Sent From STDI");
        message.setFrom(appEmail);
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_HTML_TEMPLATE_NAME_DOCTOR, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Send email
        this.mailSender.send(mimeMessage);
    }

}
