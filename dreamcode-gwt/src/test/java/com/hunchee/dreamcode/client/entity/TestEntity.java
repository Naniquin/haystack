package com.hunchee.dreamcode.client.entity;

import com.hunchee.dreamcode.client.DreamcodeObject;
import com.hunchee.dreamcode.client.entity.annotations.DreamObject;

import java.io.Serializable;

@DreamObject(kind = "MyDreamObject")
public class TestEntity implements Serializable {
    private Long parentId;
}
