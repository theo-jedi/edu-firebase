package com.theost.firebaseapp;

import com.google.firebase.firestore.DocumentId;

public class Color {
    @DocumentId
    private String id;
    private String code;

    public Color() {}

    public Color(String code) {
        this.code = code;
    }

    public Color(String id, String code) {
        this.id = id;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id='" + id + ' ' +
                ", code='" + code + ' ' +
                '}';
    }
}
