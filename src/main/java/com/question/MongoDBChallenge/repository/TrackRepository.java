package com.question.MongoDBChallenge.repository;

import com.question.MongoDBChallenge.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends MongoRepository<Track,Integer> {
    @Query("{'trackRating':{$gt:4}}")
    List<Track> findAllTracksFromRating();
    @Query("{'trackArtist.artistName':{$in:[?0]}}")
    List<Track> findAllTracksFromArtistName(String artistName);
}