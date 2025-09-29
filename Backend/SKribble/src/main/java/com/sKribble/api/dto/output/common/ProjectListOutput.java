package com.sKribble.api.dto.output.common;

import java.util.List;

public record ProjectListOutput(
    List<ProjectOutput> projectList,
    Boolean hasNext
){}
