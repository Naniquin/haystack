package com.hunchee.dreamcode.client;

import com.hunchee.twist.annotations.Id;
import com.hunchee.twist.annotations.Kind;
import com.hunchee.twist.annotations.Parent;

import java.util.Map;

/**
 * Persistence object for JSON types
 *
 * @author <a href="mailto:kerby@dotweblabs.com">Kerby Martino</a>
 * @version 1.0
 * @since 1.0
 */
public class GenericJson {
    @Id
    private Long id;
    @Kind
    private String kind;
    private Map<String,Object> fields;
    @Parent
    private User parent;

    public GenericJson(){}

    public GenericJson(Long id, String kind, Map<String, Object> fields, User parent){
        setId(id);
        setKind(kind);
        setFields(fields);
        setParent(parent);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }
}
