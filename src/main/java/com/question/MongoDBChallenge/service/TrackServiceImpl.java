package com.question.MongoDBChallenge.service;

import com.question.MongoDBChallenge.domain.Track;
import com.question.MongoDBChallenge.exception.ArtistNotFoundException;
import com.question.MongoDBChallenge.exception.TrackAlreadyExistsException;
import com.question.MongoDBChallenge.exception.TrackNotFoundException;
import com.question.MongoDBChallenge.repository.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService{
    private TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {
        if(trackRepository.findById(track.getTrackId()).isPresent()){
            throw new TrackAlreadyExistsException();
        }
        return trackRepository.save(track);
    }

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public boolean deleteTrack(int trackId) throws TrackNotFoundException {
        boolean flag = false;
        if(trackRepository.findById(trackId).isEmpty())
        {
            throw new TrackNotFoundException();
        }
        else {
            trackRepository.deleteById(trackId);
            flag = true;
        }
        return flag;
    }

    @Override
    public List<Track> getAllTracksByRatingGreaterThan4() {
        return trackRepository.findAllTracksFromRating();
    }

    @Override
    public List<Track> getAllTracksByArtistName(String artistName) throws ArtistNotFoundException {
        if(trackRepository.findAllTracksFromArtistName(artistName).isEmpty()){
            throw new ArtistNotFoundException();
        }
        return trackRepository.findAllTracksFromArtistName(artistName);
    }
}