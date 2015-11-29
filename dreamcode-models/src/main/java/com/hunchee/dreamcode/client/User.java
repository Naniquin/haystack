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
package com.hunchee.dreamcode.client;

import com.hunchee.twist.annotations.*;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
@Entity
@XStreamAlias("user")
@ApiModel
public class User implements Serializable {

    @Id
    @ApiModelProperty(position = 0, required = false, value = "Auto-generated id")
    private Long id;
    @XStreamOmitField
    @ApiModelProperty(required = true, value = "Username or email depending on the domain")
    private String username;
    @XStreamOmitField
    @ApiModelProperty(required = true, value = "Domain used to register this User, e.g. facebook, persona or local")
    private UserDomain domain;
    @XStreamOmitField
    @ApiModelProperty(required = true, value = "Base64-encoded password")
    private String passwordHash;
    @ApiModelProperty(hidden = true)
    @XStreamOmitField
    private Date created;
    @ApiModelProperty(hidden = true)
    @XStreamOmitField
    private Date modified;

    public User(){}

    public User(String username, String passwordHash, UserDomain domain){
        this.username = username;
        this.passwordHash = passwordHash;
        this.domain = domain;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDomain getDomain() {
        return domain;
    }

    public void setDomain(UserDomain domain) {
        this.domain = domain;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public static User createFrom(Registration registration){
        if(registration != null){
            User user = new User();
            user.setCreated(new Date());
            user.setModified(new Date());
            user.setDomain(UserDomain.LOCAL);
            user.setPasswordHash(registration.getPassword());
            user.setUsername(registration.getEmail());
            return user;
        }
        return null;
    }

    public enum UserDomain {
        LOCAL,
        FACEBOOK,
        PERSONA
    }

}
