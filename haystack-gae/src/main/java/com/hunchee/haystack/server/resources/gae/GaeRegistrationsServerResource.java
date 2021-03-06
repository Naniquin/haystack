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
package com.hunchee.haystack.server.resources.gae;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.inject.Inject;
import com.hunchee.haystack.client.Registration;
import com.hunchee.haystack.client.Registrations;
import com.hunchee.haystack.client.User;
import com.hunchee.haystack.server.guice.SelfInjectingServerResource;
import com.hunchee.haystack.server.resources.RegistrationsResource;
import com.hunchee.haystack.server.services.EmailService;
import com.hunchee.haystack.server.services.RegistrationService;
import com.hunchee.haystack.server.services.UserService;
import com.hunchee.haystack.server.utils.GAEUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.restlet.data.Status;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeRegistrationsServerResource extends SelfInjectingServerResource
    implements RegistrationsResource {

    private static final Logger LOG = Logger.getLogger(GaeRegistrationsServerResource.class.getName());

    String registrationString;
    String resend;
    String isForgot;

    @Inject
    RegistrationService registrationService;

    @Inject
    UserService userService;

    @Inject
    EmailService emailService;

    String username;

    String password;

    @Override
    public Registration create(Registration entity) {
        if(entity == null){
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Registration object is needed");
            return null;
        }
        LOG.info("Registration: " + entity);
        if(Boolean.parseBoolean(isForgot)){
            resetPassword(entity);
            return null;
        } else {
            LOG.info("registering new user");
            String email = entity.getEmail();
            String password = entity.getPassword();
//            String firstName = entity.getFirstName();
//            String lastName = entity.getLastName();
            //Date birthDate = entity.getBirthDate();
            if (validateEmail(email) && isResend()) {
                String registrationToken = registrationService.readToken(email);
                if (registrationToken != null) {
                    sendVerifyRegistrationEmail(email, registrationToken);
                    setStatus(Status.SUCCESS_OK);
                    return null;
                } else {
                    setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Email not registered");
                    return null;
                }
            } else if (validateEmail(email) && validatePassword(password)) {
                if (registrationService.read(email) != null || userService.read(email) != null) {
                    setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Email already registered");
                    return null;
                }
//                Long birthDateMillisec = null;
//                try {
//                    birthDateMillisec = Long.valueOf(birthDate.getTime());
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
                Registration registration = new Registration();
                //registration.setFirstName(firstName);
                //registration.setLastName(lastName);
                registration.setEmail(email);
//                registration.setBirthDate(new Date(birthDateMillisec));
                String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
                registration.setPassword(passwordHash);
                //registration.setBirthDate(birthDate);
                //registration.setFirstName(firstName);
                //registration.setLastName(lastName);

                long oneDay = 24 * 60 * 60 * 1000;
                Date expiration = new Date(new Date().getTime() + oneDay);
                registration.setExpiration(expiration);

                Key registrationKey = registrationService.create(registration);
                registration.setToken(KeyFactory.keyToString(registrationKey));
                sendVerifyRegistrationEmail(email, KeyFactory.keyToString(registrationKey));
                setStatus(Status.SUCCESS_OK);
                return null;
            } else {
                LOG.info("Error cannot register: " + entity.toString());
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            }
            getLogger().log(Level.INFO, "other");
        }
        return null;
    }

    private void resetPassword(Registration registration){
        LOG.info("resetting password");

        User user = userService.read(registration.getEmail());
        if(user == null){
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Email doesn't exist");
            return;
        }

        String passwordHash = BCrypt.hashpw(registration.getPassword(), BCrypt.gensalt());
        registration.setPassword(passwordHash);

        long oneDay = 24 * 60 * 60 * 1000;
        Date expiration = new Date(new Date().getTime() + oneDay);
        registration.setExpiration(expiration);

        Key registrationKey = registrationService.create(registration);
        registration.setToken(KeyFactory.keyToString(registrationKey));
        sendVerifyPasswordResetEmail(registration.getEmail(), KeyFactory.keyToString(registrationKey));
        setStatus(Status.SUCCESS_OK);
        return;
    }

    private boolean validateEmail(String email){
        if(email != null && !email.isEmpty()){
            return true;
        }
        return false;
    }

    private boolean validatePassword(String password){
        if(password != null && !password.isEmpty()){
            return true;
        }
        return false;
    }

    private boolean isResend(){
        if(resend != null && !resend.isEmpty()){
            if(resend.equalsIgnoreCase("true") || resend.equalsIgnoreCase("false")){
                return Boolean.valueOf(resend);
            }
        }
        return false;
    }

    private void sendVerifyRegistrationEmail(String email, String key) {
        String root = "";
        if(GAEUtil.isGaeDev()){
            LOG.info("http://localhost:9090/registrations?token="+key);
        }
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder
                .withUrl("/registrations/emails/")
                .param("email", email)
                .param("token", key)
        );
    }

    private void sendVerifyPasswordResetEmail(String email, String key) {
        if(GAEUtil.isGaeDev()){
            LOG.info("http://localhost:9090/registrations?token="+key);
        }
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder
                .withUrl("/registrations/emails/")
                .param("email", email)
                .param("token", key)
                .param("forgot", "true")
        );
    }

    @Override
    public Registrations list() {
        // TODO Add admin check
        Registrations registrations = registrationService.list();
        return registrations;
    }
}
