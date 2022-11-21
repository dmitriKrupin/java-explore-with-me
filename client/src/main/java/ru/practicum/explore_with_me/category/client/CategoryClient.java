package ru.practicum.explore_with_me.category.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore_with_me.client.BaseClient;

@Service
public class CategoryClient extends BaseClient {
    private static final String API_PREFIX = "/categories";

    @Autowired
    public CategoryClient(
            @Value("http://localhost:8088")
            String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getAllCategories() {
        return get("");
    }

    public ResponseEntity<Object> getCategoryById(@PathVariable Long catId) {
        return get("/"+ catId);
    }
}
