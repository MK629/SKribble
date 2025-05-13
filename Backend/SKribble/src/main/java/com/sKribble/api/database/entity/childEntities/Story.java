package com.sKribble.api.database.entity.childEntities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.entityFields.Chapter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.error.exceptions.CRUDExceptions.DuplicateChapterException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TypeAlias("Story")
public class Story extends Project{

    private final String title;

    private final ProjectTypes type;

    private List<Chapter> chapters;

    @PersistenceCreator
    public Story(String title, ProjectTypes type, List<Chapter> chapters, String ownerId) {
        super(ownerId);
        this.title = title;
        this.type = type;
        this.chapters = (this.chapters == null) ? new ArrayList<>() : chapters;
    }

    public void addChapter(Chapter chapter){
        
        if(this.chapters.stream().anyMatch((c) -> {return c.getChapterNumber() == chapter.getChapterNumber();})){
            throw new DuplicateChapterException(CRUDErrorMessages.DUPLICATE_CHAPTER);
        }

        this.chapters.add(chapter);

        this.chapters.sort((chapter1, chapter2) -> {return Integer.compare(chapter1.getChapterNumber(), chapter2.getChapterNumber());});
    }
}
