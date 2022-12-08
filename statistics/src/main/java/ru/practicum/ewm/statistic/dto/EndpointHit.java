package ru.practicum.ewm.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EndpointHit {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
