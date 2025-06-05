package com.sKribble.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.entity.childEntities.Song;
import com.sKribble.api.database.entity.defaults.ProjectDefaultContents;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.song.NewSongForm;
import com.sKribble.api.dto.input.song.SongTitleInput;
import com.sKribble.api.dto.output.song.SongOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.DTOConverter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SKribbleSongService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<SongOutput> findSongsByTitle(@Valid SongTitleInput songTitleInput){
        return projectRepository.findSongsByTitle(songTitleInput.title()).stream()
        .map((song) -> {
            User owner = userRepository.findByIdentification(song.getOwnerId());
            return DTOConverter.getSongOutput(song, (owner == null) ? ProjectDefaultContents.DELETED_USER : owner.getUsername());
        }).collect(Collectors.toList());
    }

    @Transactional
    public String newSong(@Valid NewSongForm newSongForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Song newSong = new Song(newSongForm.title(), ProjectTypes.Song, newSongForm.genre(), newSongForm.lyrics(), newSongForm.sheetMusicImageUrl(), invoker.getId());

        persistSong(newSong);
        
        return CRUDSuccessMessages.SONG_CREATION_SUCCESS;
    }

//==========================================[ Here lies the line for local abstractions ]================================================//

    //Get the User entity of the user who made the request (invoker).
    private User getInvoker(){
        return userRepository.findUserByUsernameOrEmail(CurrentUserInfoUtil.getCurrentUserPrincipalName());
    }

    //Create new or persist changes.
    private void persistSong(Song song){
        try{
            projectRepository.save(song);
        }
        catch(Exception e){
            throw new PersistenceErrorException(CRUDErrorMessages.PERSISTENCE_FAILED, e);
        }
    }
}
