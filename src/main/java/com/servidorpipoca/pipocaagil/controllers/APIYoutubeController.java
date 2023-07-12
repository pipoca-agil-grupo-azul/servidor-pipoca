package com.servidorpipoca.pipocaagil.controllers;

import com.servidorpipoca.pipocaagil.services.API.APIYoutubeService;
import com.servidorpipoca.pipocaagil.services.API.dto.VideoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@CrossOrigin(origins = "${CROSS_ORIGIN}", allowedHeaders = "*")
@RestController
@RequestMapping("/apiyt")
public class APIYoutubeController {

    @Autowired
    private APIYoutubeService apiYoutubeService;

    @GetMapping()
    public List<VideoDTO> getRecentVideos() throws GeneralSecurityException, IOException {
        return apiYoutubeService.getRecentVideos();
    }
}
