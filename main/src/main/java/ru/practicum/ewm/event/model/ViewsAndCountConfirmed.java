package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewsAndCountConfirmed {
    private Long views;
    private Long confirmedRequests;
}
