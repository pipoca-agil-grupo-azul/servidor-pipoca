package com.servidorpipoca.pipocaagil.services.API;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.servidorpipoca.pipocaagil.services.API.dto.VideoDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class APIYoutubeService {

    private static final String DEVELOPER_KEY = System.getenv("YOUTUBE_API_KEY");

    private static final String APPLICATION_NAME = "pipoca agil";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<VideoDTO> getRecentVideos() throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();
        YouTube.Search.List request = youtubeService.search()
                .list(Collections.singletonList("snippet"));
        SearchListResponse response = request.setKey(DEVELOPER_KEY)
                .setOrder("date")
                .setQ("Pipoca Agil")
                .setType(Collections.singletonList("video"))
                .execute();

        return convertResponseToDTOs(response);
    }

    private List<VideoDTO> convertResponseToDTOs(SearchListResponse response) {
        return response.getItems().stream()
                .map(item -> new VideoDTO(
                        item.getSnippet().getTitle(),
                        item.getSnippet().getDescription(),
                        "https://www.youtube.com/watch?v=" + item.getId().getVideoId()))
                .collect(Collectors.toList());
    }
}
