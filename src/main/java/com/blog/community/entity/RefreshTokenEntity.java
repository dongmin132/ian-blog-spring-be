package com.blog.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenEntity implements Serializable {
    private String key;

    private String value;

    public RefreshTokenEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshTokenEntity updateValue(String token) {
        this.value=token;
        return this;
    }



}

