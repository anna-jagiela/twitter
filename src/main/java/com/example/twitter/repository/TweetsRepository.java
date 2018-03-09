package com.example.twitter.repository;

import com.example.twitter.model.Tweet;
import com.example.twitter.model.User;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetsRepository extends CrudRepository<Tweet, Long>{

    List<Tweet> findByUser(@NonNull final User user);

}
