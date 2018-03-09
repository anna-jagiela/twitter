package com.example.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static lombok.AccessLevel.PACKAGE;

@Entity
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = PACKAGE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    private String name;

    public User(@NonNull String name) {
        this.name = name;
    }

}
