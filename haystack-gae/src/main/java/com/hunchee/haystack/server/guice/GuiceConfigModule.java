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
package com.hunchee.haystack.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.hunchee.haystack.server.services.*;
import com.hunchee.haystack.server.services.gae.*;
import org.restlet.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GuiceConfigModule extends AbstractModule {

    private static final Logger log = Logger.getLogger(GuiceConfigModule.class.getName());
    private Context context;

    public GuiceConfigModule(){}

    public GuiceConfigModule(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void configure() {
        // Suppress Guice warning when on GAE
        // see https://code.google.com/p/google-guice/issues/detail?id=488
        Logger.getLogger("com.google.inject.internal.util").setLevel(Level.WARNING);

        Properties configProps = readProperties();
        Names.bindProperties(binder(), configProps);
        bind(WebTokenService.class).to(GaeWebTokenService.class).in(Scopes.SINGLETON);
        bind(UserService.class).to(GaeUserService.class).in(Scopes.SINGLETON);
        bind(RegistrationService.class).to(GaeRegistrationService.class).in(Scopes.SINGLETON);
        bind(EmailService.class).to(GaeEmailService.class).in(Scopes.SINGLETON);
        bind(StoreService.class).to(GaeStoreService.class).in(Scopes.SINGLETON);
        bind(String.class).annotatedWith(Names.named("app")).toInstance("haystack");
    }

    protected Properties readProperties(){
        if(context != null){
            InputStream is =  context.getClass().getResourceAsStream("/haystack.properties");
            Properties props = new Properties();
            try {
                props.load(is);
                return props;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
