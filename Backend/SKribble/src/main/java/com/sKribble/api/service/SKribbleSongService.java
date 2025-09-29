package com.sKribble.api.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.sKribble.api.dto.output.song.SongListOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.PageNumberException;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.templates.SKribbleServiceTemplate;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.DTOConverter;
import com.sKribble.api.utils.OwnershipChecker;
import com.sKribble.api.utils.ProjectEntityUtil;


@Service
public class SKribbleSongService extends SKribbleServiceTemplate{

    //@Autowired is omitted because there's only one constructor.
    public SKribbleSongService(ProjectRepository projectRepository, UserRepository userRepository) {
        super(projectRepository, userRepository);
    }

    public SongListOutput findSongsByTitle(SongTitleInput songTitleInput, Integer page){
        if(page < 1){
            throw new PageNumberException(InputErrorMessages.INVALID_PAGE_INPUT);
        }

        Page<Song> songList = projectRepository.findSongsByTitle(songTitleInput.title(), PageRequest.of(page - 1, 5));

        return DTOConverter.getSongListOutput(
            songList.stream()
            .map((song) -> {
                User owner = userRepository.findByIdentification(song.getOwnerId());
                return DTOConverter.getSongOutput(song, (owner == null) ? ProjectDefaultContents.DELETED_USER : owner.getUsername());
            }).collect(Collectors.toList()), 
            songList.hasNext()
        );
    }

    @Transactional
    public String newSong(NewSongForm newSongForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Song newSong = new Song(newSongForm.title(), ProjectTypes.Song, newSongForm.genre(), newSongForm.lyrics(), newSongForm.sheetMusicImageUrl(), invoker.getId());

        persistProject(newSong);
        
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

        persistProject(songToEdit);

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

        persistProject(songToChangeSheetMusicImage);

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

        persistProject(songToChangeGenre);

        return CRUDSuccessMessages.SONG_GENRE_CHANGE_SUCCESS;
    }
}
