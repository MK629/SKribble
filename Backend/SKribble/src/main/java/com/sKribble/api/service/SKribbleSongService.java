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
import com.sKribble.api.dto.input.song.ChangeSongGenreForm;
import com.sKribble.api.dto.input.song.ChangeSongSheetMusicImageForm;
import com.sKribble.api.dto.input.song.EditSongForm;
import com.sKribble.api.dto.input.song.NewSongForm;
import com.sKribble.api.dto.input.song.SongTitleInput;
import com.sKribble.api.dto.output.song.SongOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.DTOConverter;
import com.sKribble.api.utils.OwnershipChecker;
import com.sKribble.api.utils.ProjectEntityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SKribbleSongService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<SongOutput> findSongsByTitle(SongTitleInput songTitleInput){
        return projectRepository.findSongsByTitle(songTitleInput.title()).stream()
        .map((song) -> {
            User owner = userRepository.findByIdentification(song.getOwnerId());
            return DTOConverter.getSongOutput(song, (owner == null) ? ProjectDefaultContents.DELETED_USER : owner.getUsername());
        }).collect(Collectors.toList());
    }

    @Transactional
    public String newSong(NewSongForm newSongForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Song newSong = new Song(newSongForm.title(), ProjectTypes.Song, newSongForm.genre(), newSongForm.lyrics(), newSongForm.sheetMusicImageUrl(), invoker.getId());

        persistSong(newSong);
        
        return CRUDSuccessMessages.SONG_CREATION_SUCCESS;
    }

    @Transactional
    public String editSong(EditSongForm editSongForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Song songToEdit = projectRepository.findSongById(editSongForm.songId());

        ProjectEntityUtil.checkExistence(songToEdit);

        OwnershipChecker.checkOwnership(invoker, songToEdit);

        songToEdit.editSong(editSongForm.title(), editSongForm.lyrics());

        persistSong(songToEdit);

        return CRUDSuccessMessages.SONG_EDIT_SUCCESS;
    }

    @Transactional
    public String changeSongSheetMusicImage(ChangeSongSheetMusicImageForm changeSongSheetMusicImageForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Song songToChangeSheetMusicImage = projectRepository.findSongById(changeSongSheetMusicImageForm.songId());

        ProjectEntityUtil.checkExistence(songToChangeSheetMusicImage);

        OwnershipChecker.checkOwnership(invoker, songToChangeSheetMusicImage);

        songToChangeSheetMusicImage.changeSongSheetMusicImage(changeSongSheetMusicImageForm.sheetMusicImageUrl());

        persistSong(songToChangeSheetMusicImage);

        return CRUDSuccessMessages.SONG_SHEET_MUSIC_IMAGE_UPLOAD_SUCCESS;
    }

    @Transactional
    public String changeSongGenre(ChangeSongGenreForm changeSongGenreForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Song songToChangeGenre = projectRepository.findSongById(changeSongGenreForm.songId());

        ProjectEntityUtil.checkExistence(songToChangeGenre);

        OwnershipChecker.checkOwnership(invoker, songToChangeGenre);

        songToChangeGenre.changeGenre(changeSongGenreForm.newGenre());

        persistSong(songToChangeGenre);

        return CRUDSuccessMessages.SONG_GENRE_CHANGE_SUCCESS;
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
