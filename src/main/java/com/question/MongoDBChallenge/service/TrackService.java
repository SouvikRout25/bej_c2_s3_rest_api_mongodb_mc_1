package com.question.MongoDBChallenge.service;

import com.question.MongoDBChallenge.domain.Track;
import com.question.MongoDBChallenge.exception.ArtistNotFoundException;
import com.question.MongoDBChallenge.exception.TrackAlreadyExistsException;
import com.question.MongoDBChallenge.exception.TrackNotFoundException;

import java.util.List;

public interface TrackService {
    Track saveTrack(Track track) throws TrackAlreadyExistsException;
    List<Track> getAllTracks();
    boolean deleteTrack(int trackId) throws TrackNotFoundException;
    List<Track> getAllTracksByRatingGreaterThan4();
    List<Track> getAllTracksByArtistName(String artistName) throws ArtistNotFoundException;
}