package com.example.twitter.repository;

import com.example.twitter.model.Subscription;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionsRepository extends CrudRepository<Subscription, Long>{

    List<Subscription> findByFollowerId(@NonNull final Long from);

}
