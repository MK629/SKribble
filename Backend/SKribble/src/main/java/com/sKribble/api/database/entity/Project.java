package com.sKribble.api.database.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "projects")
@Getter
@Setter
@TypeAlias("Project")
public class Project {

    @Id
    @NonNull
    String id;

    @NonNull
    String name;

    protected Project(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
