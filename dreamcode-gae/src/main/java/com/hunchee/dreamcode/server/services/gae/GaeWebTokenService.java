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

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.hunchee.dreamcode.server.services.WebTokenService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GaeWebTokenService implements WebTokenService {

    private static final Logger LOG
            = Logger.getLogger(GaeWebTokenService.class.getName());

    private String secret;
    private JWTSigner signer;
    private JWTVerifier verifier;

    @Inject
    public GaeWebTokenService(@Named("app.secret") String secret){
        this.secret = secret;
        signer = new JWTSigner(secret);
        verifier = new JWTVerifier(secret);
    }

    @Override
    public String createToken(Long userId){
        HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", String.valueOf(userId));
        String token = signer.sign(claims);
        LOG.info("Generated token: " + token);
        return token;
    }

    @Override
    public Map<String, Object> readToken() {
        return null;
    }

    @Override
    public Long readUserIdFromToken(String token) {
        Long id = null;
        try {
            Map<String, Object> parsed = verifier.verify(token);
            Object objectId = parsed.get("id");
            if(objectId instanceof Integer){
                id = Long.valueOf((Integer)objectId);
            } else if (objectId instanceof Long) {
                id = (Long) objectId;
            } else if(objectId instanceof String){
                id = Long.valueOf((String) objectId);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (JWTVerifyException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }
}
