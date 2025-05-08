package com.sKribble.api.database.entity.children;

import org.springframework.data.annotation.TypeAlias;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.enums.ProjectTypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TypeAlias("Story")
public class Story extends Project{

    private final ProjectTypes type = ProjectTypes.Story;

    public Story(String name) {
        super(name);
    }
}
