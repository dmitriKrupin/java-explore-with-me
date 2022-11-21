package ru.practicum.explore_with_me.category.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.client.BaseClient;

import javax.validation.constraints.PositiveOrZero;

@Service
public class AdminCategoryClient extends BaseClient {
    private static final String API_PREFIX = "/admin/categories";

    @Autowired
    public AdminCategoryClient(
            @Value("http://localhost:8088") String serverUrl,
            RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> updateCategory(
            @Validated @RequestBody CategoryDto categoryDto) {
        return patch("", categoryDto);
    }

    public ResponseEntity<Object> addCategory(
            @Validated @RequestBody NewCategoryDto newCategoryDto) {
        return post("", newCategoryDto);
    }

    public ResponseEntity<Object> deleteCategory(
            @Validated @PositiveOrZero @PathVariable Long catId) {
        return delete("/"+ catId);
    }
}
