package com.sKribble.api.utils;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.error.exceptions.CRUDExceptions.AssetNotOwnedException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;

/**
 * <h4>A util class to check the ownership of projects.</h4>
 * <br/>
 * <h5>Used to ensure that only authenticated users who currently hold ownership of the project, may make changes to said project.</h5>
 */
public class OwnershipChecker {

    public static void checkOwnership(User user, Project project){
        if(!user.getId().equals(project.getOwnerId())){
            throw new AssetNotOwnedException(CRUDErrorMessages.ASSET_NOT_OWNED);
        }
    }
}
