package com.sKribble.api.database.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "projects")
@Getter
@Setter
@TypeAlias("Project")
public class Project {

    @Id
    @NonNull
    private String id;

    @Nonnull
    private String ownerId;

    protected Project(String ownerId){
        this.id = UUID.randomUUID().toString();
        this.ownerId = ownerId;
    }

    public void changeOwnerShip(String newOwnerId){
        this.ownerId = newOwnerId;
    }
}
