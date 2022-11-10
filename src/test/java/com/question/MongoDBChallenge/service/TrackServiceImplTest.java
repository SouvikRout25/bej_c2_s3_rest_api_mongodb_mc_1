package com.question.MongoDBChallenge.service;

import com.question.MongoDBChallenge.domain.Artist;
import com.question.MongoDBChallenge.domain.Track;
import com.question.MongoDBChallenge.exception.TrackAlreadyExistsException;
import com.question.MongoDBChallenge.exception.TrackNotFoundException;
import com.question.MongoDBChallenge.repository.TrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackServiceImpl trackService;

    private Track track1,track2;
    Artist artist1,artist2;
    List<Track> trackList;
    @BeforeEach
    void setUp() {
        artist1=new Artist(1,"Badshah");
        artist2=new Artist(2,"Shreya");
        track1=new Track(1,"Mercy",7,artist1);
        track2=new Track(2,"Pal",6,artist2);
        // trackList=trackRepository.findAll();
        trackList= Arrays.asList(track1,track2);
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    public void saveTrack() throws TrackAlreadyExistsException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
        when(trackRepository.save(any())).thenReturn(track1);
        assertEquals(track1,trackService.saveTrack(track1));
        verify(trackRepository,times(1)).save(any());
        verify(trackRepository,times(1)).findById(any());
    }

    @Test
    public void deleteTrack() throws TrackNotFoundException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        boolean flag = trackService.deleteTrack(track1.getTrackId());
        assertEquals(true,flag);

        verify(trackRepository,times(1)).deleteById(any());
        verify(trackRepository,times(1)).findById(any());
    }
    @Test
    public void returnTrackFailure(){
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        assertThrows(TrackAlreadyExistsException.class,()->trackService.saveTrack(track1));
        verify(trackRepository,times(0)).save(any());
        verify(trackRepository,times(1)).findById(any());

    }
    @Test
    public void deleteTrackFailure(){
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
        assertThrows(TrackNotFoundException.class,()-> trackService.deleteTrack(track1.getTrackId()));
        verify(trackRepository,times(0)).save(any());
        verify(trackRepository,times(1)).findById(any());
    }
}