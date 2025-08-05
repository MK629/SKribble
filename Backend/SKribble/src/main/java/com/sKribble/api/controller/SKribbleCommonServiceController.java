package com.sKribble.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sKribble.api.dto.input.common.DeleteProjectForm;
import com.sKribble.api.service.SKribbleCommonService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("${SKribble.common.service.path}")
@RequiredArgsConstructor
public class SKribbleCommonServiceController {

    private final SKribbleCommonService sKribbleCommonService;

    @PostMapping("/deleteProject")
    public ResponseEntity<String> deleteProject(@RequestBody DeleteProjectForm deleteProjectForm) {
        return sKribbleCommonService.deleteProject(deleteProjectForm);
    }
}
