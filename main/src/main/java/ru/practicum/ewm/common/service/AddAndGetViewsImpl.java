package ru.practicum.ewm.common.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.common.DataForStatistics;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.ViewsAndCountConfirmed;
import ru.practicum.ewm.request.dto.ViewStats;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AddAndGetViewsImpl implements AddAndGetViews {
    @Value("${stats-server.url}")
    private String statsPath;

    @Override
    public void addHit(String uri, String ip) {
        URI url = URI.create(statsPath + "/hit");
        DataForStatistics data = new DataForStatistics(
                "ewm-main-service", uri, ip,
                LocalDateTime.now().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")));
        Gson gson = new Gson();
        String likeSerialized = gson.toJson(data);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(likeSerialized))
                .uri(url)
                .header("Content-Type", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Event, ViewsAndCountConfirmed> getViewsAndCountConfirmedOfEvents(
            Map<Event, Long> eventsWithCountConfirmed) {
        List<String> toStatisticService = new ArrayList<>();
        for (Map.Entry<Event, Long> entry : eventsWithCountConfirmed.entrySet()) {
            toStatisticService.add("/events/" + entry.getKey().getId());
        }
        Map<Long, Long> viewsOfEventsId =
                getViewsOfEventsId(toStatisticService.toString());
        Map<Event, ViewsAndCountConfirmed> viewsAndCountConfirmedOfEvents = new HashMap<>();
        for (Map.Entry<Event, Long> entryFromConfirmed : eventsWithCountConfirmed.entrySet()) {
            Long views = 0L;
            for (Map.Entry<Long, Long> entryFromView : viewsOfEventsId.entrySet()) {
                if (entryFromView.getKey().equals(entryFromConfirmed.getKey().getId())) {
                    views = entryFromView.getValue();
                }
            }
            viewsAndCountConfirmedOfEvents.put(
                    entryFromConfirmed.getKey(),
                    new ViewsAndCountConfirmed(
                            views,
                            entryFromConfirmed.getValue()
                    )
            );
        }
        return viewsAndCountConfirmedOfEvents;
    }

    @Override
    public Map<Long, Long> getViewsOfEventsId(String uris) {
        HttpGet someHttpGet = new HttpGet(statsPath + "/stats");
        URI uri = null;
        try {
            uri = new URIBuilder(someHttpGet.getURI())
                    .addParameter("uris", uris
                            .replace("[", "")
                            .replace("]", ""))
                    .build();
        } catch (URISyntaxException exception) {
            log.error(exception.getCause().getMessage());
        }
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Accept", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
        } catch (IOException | InterruptedException exception) {
            log.error(exception.getCause().getMessage());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        assert response != null;
        String answer = response.body();
        objectMapper.configure(DeserializationFeature
                .USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        ViewStats[] viewStatsArray = new ViewStats[0];
        try {
            viewStatsArray = objectMapper.readValue(answer, ViewStats[].class);
        } catch (IOException exception) {
            log.error(exception.getCause().getMessage());
        }
        Map<Long, Long> viewsOfEventsUri = new HashMap<>();
        for (ViewStats entry : viewStatsArray) {
            viewsOfEventsUri.put(
                    Long.parseLong(entry.getUri().replace("/events/", "")),
                    entry.getHits());
        }
        return viewsOfEventsUri;
    }

    @Override
    public Long getViewByEventId(String uris) {
        HttpGet someHttpGet = new HttpGet(statsPath + "/stats");
        URI uri = null;
        try {
            uri = new URIBuilder(someHttpGet.getURI())
                    .addParameter("uris", uris)
                    .build();
        } catch (URISyntaxException exception) {
            log.error(exception.getCause().getMessage());
        }
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Accept", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
        } catch (IOException | InterruptedException exception) {
            log.error(exception.getCause().getMessage());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        assert response != null;
        String answer = response.body();
        objectMapper.configure(DeserializationFeature
                .USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        ViewStats[] viewStatsArray = new ViewStats[0];
        try {
            viewStatsArray = objectMapper.readValue(answer, ViewStats[].class);
        } catch (IOException exception) {
            log.error(exception.getCause().getMessage());
        }
        if (viewStatsArray.length > 0) {
            ViewStats viewStats = viewStatsArray[0];
            return viewStats.getHits();
        } else {
            return 0L;
        }
    }
}
