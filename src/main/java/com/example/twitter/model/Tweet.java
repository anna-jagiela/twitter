package com.example.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.util.Date;

import static lombok.AccessLevel.PACKAGE;

@Entity
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = PACKAGE)
public class Tweet {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @ManyToOne
    private User user;

    @JsonProperty
    private Date createdAt;

    @JsonProperty
    private String message;


    public Tweet(@NonNull final User user,
                 @NonNull final String message) {
        this.user = user;
        this.createdAt = new Date();
        this.message = message;
    }

}
