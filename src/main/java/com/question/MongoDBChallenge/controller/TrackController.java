package com.question.MongoDBChallenge.controller;

import com.question.MongoDBChallenge.domain.Track;
import com.question.MongoDBChallenge.exception.ArtistNotFoundException;
import com.question.MongoDBChallenge.exception.TrackAlreadyExistsException;
import com.question.MongoDBChallenge.exception.TrackNotFoundException;
import com.question.MongoDBChallenge.service.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackData/")
public class TrackController {
    private TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }
    @PostMapping(value = "/track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) throws TrackAlreadyExistsException{
        ResponseEntity responseEntity = null;
        try{
            responseEntity = new ResponseEntity<>(trackService.saveTrack(track), HttpStatus.CREATED);
        }catch(TrackAlreadyExistsException e){
            throw new TrackAlreadyExistsException();
        }catch(Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping(value = "/tracks")
    public ResponseEntity<?> getAllTracks(){
        ResponseEntity responseEntity = null;
        try{
            responseEntity = new ResponseEntity(trackService.getAllTracks(), HttpStatus.OK);
        }catch (Exception exception){
            responseEntity = new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @DeleteMapping(value = "/track/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable int trackId) throws TrackNotFoundException {
        ResponseEntity responseEntity = null;
        try {
            trackService.deleteTrack(trackId);
            responseEntity = new ResponseEntity("Successfully deleted !!!", HttpStatus.OK);
        } catch (TrackNotFoundException e) {
            throw new TrackNotFoundException();
        }
        catch (Exception exception){
            responseEntity = new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping(value = "/track4")
    public ResponseEntity<?> getAllTracksByRatingGreaterThan4(){
        ResponseEntity responseEntity = null;
        try{
            responseEntity = new ResponseEntity<>(trackService.getAllTracksByRatingGreaterThan4(),HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping(value = "/track/{artistName}")
    public ResponseEntity<?> getAllTracksByArtistName(@PathVariable String artistName) throws ArtistNotFoundException {
        ResponseEntity responseEntity = null;
        try{
            responseEntity = new ResponseEntity(trackService.getAllTracksByArtistName(artistName),HttpStatus.FOUND);
        }catch(ArtistNotFoundException exception){
            throw new ArtistNotFoundException();
        }catch(Exception e){
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}