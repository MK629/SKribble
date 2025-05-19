package com.sKribble.api.utils;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.error.exceptions.CRUDExceptions.AssetNotOwnedException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;

public class OwnershipChecker {

    public static void checkOwnership(User user, Project project){
        if(!user.getId().equals(project.getOwnerId())){
            throw new AssetNotOwnedException(CRUDErrorMessages.ASSET_NOT_OWNED);
        }
    }
}
