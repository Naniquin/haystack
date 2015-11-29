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
package com.hunchee.dreamcode.client.account;

import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.hunchee.dreamcode.client.DreamcodeCallback;
import com.hunchee.dreamcode.client.entity.Registration;
import com.hunchee.dreamcode.client.proxy.AccountResourceProxy;
import com.hunchee.dreamcode.client.proxy.RegistrationResourceProxy;
import com.hunchee.dreamcode.client.proxy.TokenResourceProxy;
import org.restlet.client.data.ChallengeResponse;
import org.restlet.client.data.ChallengeScheme;
import org.restlet.client.representation.Representation;
import org.restlet.client.resource.Result;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class Account extends AbstractAccount {

    @Override
    public void signUp(String username, String password, final DreamcodeCallback<Boolean> callback) {
        Preconditions.checkNotNull(username, "Username cannot be null");
        Preconditions.checkNotNull(password, "Password cannot be null");
        RegistrationResourceProxy resourceProxy = GWT.create(RegistrationResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "signups");
        resourceProxy.getClientResource().addQueryParameter("username", username);
        resourceProxy.getClientResource().addQueryParameter("password", password);
        Registration registration = new Registration(username, password);
        resourceProxy.signup(registration, new Result<Boolean>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.failure(throwable);
            }
            @Override
            public void onSuccess(Boolean aBoolean) {
                callback.success(aBoolean);
            }
        });
    }

    @Override
    public void signIn(String username, String password, final DreamcodeCallback<String> callback) {
        TokenResourceProxy resourceProxy = GWT.create(TokenResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "tokens");
        ChallengeResponse cr = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, username, password);
        resourceProxy.getClientResource().setChallengeResponse(cr);
        resourceProxy.signin(new Result<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.failure(throwable);
            }
            @Override
            public void onSuccess(String clientToken) {
                setClientToken(clientToken);
                callback.success(clientToken);
            }
        });
    }

    @Override
    public void signOut(DreamcodeCallback<Void> callback) {
        setClientToken(null);
    }

    @Override
    public void changePassword(String newPassword, String currentPassword, DreamcodeCallback<Boolean> callback) {
        AccountResourceProxy resourceProxy = GWT.create(AccountResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "accounts");
        ChallengeResponse cr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        cr.setRawValue(getClientToken());
        resourceProxy.getClientResource().addQueryParameter("new_password", newPassword);
        resourceProxy.getClientResource().addQueryParameter("current_password", currentPassword);
        resourceProxy.getClientResource().setChallengeResponse(cr);
        resourceProxy.update(new Result<Representation>() {
            @Override
            public void onFailure(Throwable throwable) {

            }
            @Override
            public void onSuccess(Representation s) {

            }
        });
    }

    @Override
    public void changeUsername(String newUsername, String currentPassword, DreamcodeCallback<Boolean> callback) {
        AccountResourceProxy resourceProxy = GWT.create(AccountResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "accounts");
        ChallengeResponse cr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        cr.setRawValue(getClientToken());
        resourceProxy.getClientResource().addQueryParameter("new_username", newUsername);
        resourceProxy.getClientResource().addQueryParameter("current_password", currentPassword);
        resourceProxy.getClientResource().setChallengeResponse(cr);
        resourceProxy.update(new Result<Representation>() {
            @Override
            public void onFailure(Throwable throwable) {

            }
            @Override
            public void onSuccess(Representation s) {

            }
        });
    }

    @Override
    public void resetPassword(String currentUsername, final DreamcodeCallback<Boolean> callback) {
        RegistrationResourceProxy resourceProxy = GWT.create(RegistrationResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "signups");
        resourceProxy.getClientResource().addQueryParameter("is_forgot", "true");
        resourceProxy.signup(null, new Result<Boolean>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.failure(throwable);
            }
            @Override
            public void onSuccess(Boolean aBoolean) {
                callback.success(aBoolean);
            }
        });
    }

    @Override
    public void destroy(DreamcodeCallback<Boolean> callback) {
        AccountResourceProxy resourceProxy = GWT.create(AccountResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "accounts");
        ChallengeResponse cr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        cr.setRawValue(getClientToken());
        resourceProxy.getClientResource().setChallengeResponse(cr);
        resourceProxy.destroy(new Result<Representation>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(Representation representation) {

            }
        });
    }

    @Override
    public void get(String field, DreamcodeCallback<String> callback) {
        AccountResourceProxy resourceProxy = GWT.create(AccountResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "accounts");
        ChallengeResponse cr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        cr.setRawValue(getClientToken());
        resourceProxy.getClientResource().setChallengeResponse(cr);
        resourceProxy.getClientResource().addQueryParameter("field", field);
        resourceProxy.get(new Result<Representation>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(Representation representation) {

            }
        });
    }

    @Override
    public void change(String field, String newValue, DreamcodeCallback<Boolean> callback) {
        AccountResourceProxy resourceProxy = GWT.create(AccountResourceProxy.class);
        resourceProxy.getClientResource().setReference(getServerRoot() + "accounts");
        ChallengeResponse cr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        cr.setRawValue(getClientToken());
        resourceProxy.getClientResource().setChallengeResponse(cr);
        resourceProxy.getClientResource().addQueryParameter("field", field);
        resourceProxy.update(new Result<Representation>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(Representation representation) {

            }
        });
    }
}
