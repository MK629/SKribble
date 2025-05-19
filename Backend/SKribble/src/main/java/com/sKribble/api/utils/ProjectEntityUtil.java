package com.sKribble.api.utils;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.error.exceptions.CRUDExceptions.ProjectNotFoundException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;

public class ProjectEntityUtil {

    public static void checkExistence(Project project){
        if(project == null){
            throw new ProjectNotFoundException(CRUDErrorMessages.PROJECT_NOT_FOUND);
        }
    }
}
