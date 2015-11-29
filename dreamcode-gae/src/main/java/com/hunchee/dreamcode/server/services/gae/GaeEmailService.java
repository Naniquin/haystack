/**
 *
 * Copyright (c) 2015 Dotweblabs Web Technologies and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *     __                                         __
 * .--|  .----.-----.---.-.--------.----.-----.--|  .-----.
 * |  _  |   _|  -__|  _  |        |  __|  _  |  _  |  -__|
 * |_____|__| |_____|___._|__|__|__|____|_____|_____|_____|
 *
 */
package com.hunchee.dreamcode.server.services.gae;

import com.hunchee.dreamcode.server.services.EmailService;
import com.postmark.PostmarkMailSender;
import com.postmark.PostmarkMessage;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeEmailService implements EmailService {

    @Override
    public void sendEmail(String to, String from, String subj, String body){
        PostmarkMailSender mailSender = new PostmarkMailSender("1c317048-8706-49c2-9e6c-1ea38def3add");
        PostmarkMessage message = new PostmarkMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subj);
        message.setHtmlBody(body);
        mailSender.send(message);
        System.out.println("Email Sent!");
    }

}
