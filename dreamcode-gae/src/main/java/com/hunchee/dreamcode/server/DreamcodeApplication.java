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
package com.hunchee.dreamcode.server;

import com.google.inject.Guice;
import com.hunchee.dreamcode.server.guice.GuiceConfigModule;
import com.hunchee.dreamcode.server.guice.SelfInjectingServerResourceModule;
import com.hunchee.dreamcode.server.resources.gae.GaeRegistrationsServerResource;
import com.hunchee.dreamcode.server.resources.gae.*;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.ext.swagger.SwaggerApplication;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class DreamcodeApplication extends SwaggerApplication {
    private static final String ROOT_URI = "/";

  /**
   * Creates a root Restlet that will receive all incoming calls.
   */
  @Override
  public Restlet createInboundRoot() {

    Guice.createInjector(new GuiceConfigModule(this.getContext()),
            new SelfInjectingServerResourceModule());

    configureConverters();

    // Create a guard
    ChallengeAuthenticator guard = new ChallengeAuthenticator(
    getContext(), ChallengeScheme.HTTP_OAUTH_BEARER, "A OAuth challenge authentication scheme.");

    Router router = new Router(getContext());
    router.attach(ROOT_URI, GaeRootServerResource.class);
    router.attach(ROOT_URI + "ping", GaePingServerResource.class);
    router.attach(ROOT_URI + "signups", GaeRegistrationsServerResource.class);
    router.attach(ROOT_URI + "signups/{token}", GaeRegistrationServerResource.class);
    router.attach(ROOT_URI + "tokens", GaeTokensServerResource.class);
    router.attach(ROOT_URI + "accounts", GaeAccountsServerResource.class);
    router.attach(ROOT_URI + "accounts/{account_id}", GaeAccountServerResource.class);
    router.attach(ROOT_URI + "stores", GaeStoresResource.class);

    return router;
  }

  private Map<String,String> dreamcodeProperties(){
    Map<String,String> map = new LinkedHashMap<String, String>();
    InputStream is =  getContext().getClass().getResourceAsStream("/dreamcode.properties");
    Properties props = new Properties();
    try {
      props.load(is);
      map = new LinkedHashMap<String, String>((Map) props);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return map;
  }

  private void configureConverters() {
    List<ConverterHelper> converters = Engine.getInstance()
            .getRegisteredConverters();
    JacksonConverter jacksonConverter = null;
    for (ConverterHelper converterHelper : converters) {
      System.err.println(converterHelper.getClass());
      if (converterHelper instanceof JacksonConverter) {
        jacksonConverter = (JacksonConverter) converterHelper;
        break;
      }
    }
    if (jacksonConverter != null) {
      Engine.getInstance()
              .getRegisteredConverters().remove(jacksonConverter);
    }
  }

}