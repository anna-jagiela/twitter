package com.example.twitter.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static lombok.AccessLevel.PACKAGE;

@Entity
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = PACKAGE)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerId;

    private Long followeeId;

    public Subscription(@NonNull final Long followerId,
                        @NonNull final Long followeeId) {
       this.followerId = followerId;
       this.followeeId = followeeId;
    }

}
