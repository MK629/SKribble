package com.sKribble.api.utils;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.error.exceptions.CRUDExceptions.ProjectNotFoundException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;

/**
 * A util containing common checks for projects.
 */
public class ProjectEntityUtil {

    public static void checkExistence(Project project){
        if(project == null){
            throw new ProjectNotFoundException(CRUDErrorMessages.PROJECT_NOT_FOUND);
        }
    }
}
