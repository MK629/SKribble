package com.sKribble.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sKribble.api.dto.input.common.ChangeOwnershipForm;
import com.sKribble.api.dto.input.common.DeleteProjectForm;
import com.sKribble.api.dto.output.common.ProjectListOutput;
import com.sKribble.api.service.SKribbleCommonService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("${SKribble.common.service.path}")
@RequiredArgsConstructor
public class SKribbleCommonServiceController {

    private final SKribbleCommonService sKribbleCommonService;
    
    @GetMapping("/getCurrentUserProjects/{page}")
    public ResponseEntity<ProjectListOutput> getCurrentUserProjects(@PathVariable("page") Integer page) {
        return sKribbleCommonService.getCurrentUserProjects(page);
    }

    @PostMapping("/changeOwnership")
    public ResponseEntity<String> changeOwnership(@RequestBody ChangeOwnershipForm changeOwnershipForm) {
        return sKribbleCommonService.changeOwnership(changeOwnershipForm);
    }
    
    @PostMapping("/deleteProject")
    public ResponseEntity<String> deleteProject(@RequestBody DeleteProjectForm deleteProjectForm) {
        return sKribbleCommonService.deleteProject(deleteProjectForm);
    }
}
