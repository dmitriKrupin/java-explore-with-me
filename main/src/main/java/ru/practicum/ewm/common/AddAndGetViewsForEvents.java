package ru.practicum.ewm.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.ViewStats;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AddAndGetViewsForEvents {
    public String statsPath;

    public AddAndGetViewsForEvents(String statsPath) {
        this.statsPath = statsPath;
    }

    public void addHit(String uri, String ip)
            throws IOException, InterruptedException {
        URI url = URI.create(statsPath + "/hit");
        Map<Object, Object> data = new HashMap<>();
        data.put("app", "ewm-main-service");
        data.put("uri", uri);
        data.put("ip", ip);
        data.put("timestamp", LocalDateTime.now().format(DateTimeFormatter
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
        HttpResponse<String> response = client.send(request, handler);
    }

    public Map<Event, Long> getMapViewsOfEvents(List<Event> events) {
        Map<Event, Long> viewsOfEvents = new HashMap<>();
        for (Event entry : events) {
            Long views = 0L;
            try {
                views = getViewByEventId("/events/" + entry.getId());
            } catch (Exception exception) {
                log.error(exception.getCause().getMessage());
            }
            viewsOfEvents.put(entry, views);
        }
        return viewsOfEvents;
    }

    public Long getViewByEventId(String uris)
            throws IOException, InterruptedException, URISyntaxException {
        HttpGet someHttpGet = new HttpGet(statsPath + "/stats");
        URI uri = new URIBuilder(someHttpGet.getURI())
                .addParameter("uris", uris)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Accept", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        ObjectMapper objectMapper = new ObjectMapper();
        String answer = response.body();
        objectMapper.configure(DeserializationFeature
                .USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        ViewStats[] viewStatsArray = objectMapper.readValue(answer, ViewStats[].class);
        if (viewStatsArray.length > 0) {
            ViewStats viewStats = viewStatsArray[0];
            return viewStats.getHits();
        } else {
            return 0L;
        }
    }
}
