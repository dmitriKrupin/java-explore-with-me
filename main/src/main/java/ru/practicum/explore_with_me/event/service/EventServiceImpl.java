package ru.practicum.explore_with_me.event.service;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.category.model.Category;
import ru.practicum.explore_with_me.category.repository.CategoryRepository;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.dto.EventShortDto;
import ru.practicum.explore_with_me.event.dto.NewEventDto;
import ru.practicum.explore_with_me.event.mapper.EventMapper;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.model.Location;
import ru.practicum.explore_with_me.event.model.Status;
import ru.practicum.explore_with_me.event.repository.EventRepository;
import ru.practicum.explore_with_me.event.repository.LocationRepository;
import ru.practicum.explore_with_me.exception.NotFoundException;
import ru.practicum.explore_with_me.request.dto.AdminUpdateEventRequest;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.dto.UpdateEventRequest;
import ru.practicum.explore_with_me.request.mapper.RequestMapper;
import ru.practicum.explore_with_me.request.model.Request;
import ru.practicum.explore_with_me.request.repository.RequestRepository;
import ru.practicum.explore_with_me.user.model.User;
import ru.practicum.explore_with_me.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository, RequestRepository requestRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<EventShortDto> getAllEvents(
            String text, String categories, Boolean paid, String rangeStart,
            String rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size,
            HttpServletRequest request) throws IOException, InterruptedException {
        List<Event> events = eventRepository
                .findAllByAnnotationLikeAndCategoryIdAndPaid(
                        text, Long.parseLong(categories), paid);
        addHit(request.getRequestURI(), request.getRemoteAddr());
        return EventMapper.toEventShortDtoList(events);
    }

    @Override
    public EventFullDto getEventById(Long id, HttpServletRequest request) throws IOException, InterruptedException {
        addHit(request.getRequestURI(), request.getRemoteAddr());
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + id + " нет"));
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Такой категории c id " + id + " нет"));
        EventFullDto eventFullDto;
        if (event.getState().equals(Status.PUBLISHED)) {
            eventFullDto = EventMapper.toPublishedEventFullDto(event, category);
        } else {
            eventFullDto = EventMapper.toEventFullDto(event, category);
        }
        return eventFullDto;
    }

    private void addHit(String uri, String ip) throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:9090/hit");
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

    @Override
    public List<EventShortDto> getAllEventsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        List<Event> eventsByUser = eventRepository.findAllByInitiator(user);
        return EventMapper.toEventShortDtoList(eventsByUser);
    }

    @Override
    public EventFullDto updateEventByUser(Long userId, UpdateEventRequest updateEventRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Long eventId = updateEventRequest.getEventId();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            event.setAnnotation(updateEventRequest.getAnnotation());

            Long categoryId = updateEventRequest.getCategory();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Такой категории c id " + categoryId + " нет"));
            event.setCategory(category);

            event.setDescription(updateEventRequest.getDescription());
            event.setEventDate(LocalDateTime.parse(updateEventRequest.getEventDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            event.setPaid(updateEventRequest.getPaid());
            event.setParticipantLimit(updateEventRequest.getParticipantLimit());
            event.setTitle(updateEventRequest.getTitle());

            eventRepository.save(event);
            EventFullDto eventFullDto;
            if (event.getState().equals(Status.PUBLISHED)) {
                eventFullDto = EventMapper.toPublishedEventFullDto(event, category);
            } else {
                eventFullDto = EventMapper.toEventFullDto(event, category);
            }
            return eventFullDto;
        } else {
            throw new RuntimeException("Пользователь с id " + userId + " не может вносить изменения в это событие.");
        }
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new RuntimeException("Такой категории c id " + newEventDto.getCategory() + " нет"));
        addLocation(newEventDto);
        Long views = 0L;
        Event event = EventMapper
                .toNewEvent(newEventDto, category, user, Status.PENDING, views);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event, category);
    }

    private Long getView(/*String start, String end, */List<String> uris/*, Boolean unique*/)
            throws IOException, InterruptedException, URISyntaxException {
        HttpGet someHttpGet = new HttpGet("http://localhost:9090/stats");
        URI uri = new URIBuilder(someHttpGet.getURI())
                //.addParameter("start", start)
                //.addParameter("end", end)
                .addParameter("uris", String.valueOf(uris))
                //.addParameter("unique", String.valueOf(unique))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Accept", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        String answer = response.body();
        System.out.println(answer);
        return null;
    }

    @Override
    public Location addLocation(NewEventDto newEventDto) {
        Location location = newEventDto.getLocation();
        locationRepository.save(location);
        return location;
    }

    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        return EventMapper.toEventFullDto(event, category);
    }

    @Override
    public EventFullDto cancelEventByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        if (event.getState().equals(Status.PENDING)) {
            event.setState(Status.CANCELED);
            return EventMapper.toEventFullDto(event, category);
        } else {
            throw new NotFoundException("Событие с id " + eventId + " не может быть отменено, т.к. прошло модерацию.");
        }
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        List<Request> requests = requestRepository.findAllByEventId(eventId);
        return RequestMapper.toParticipationRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto confirmedRequestByUser(Long userId, Long eventId, Long reqId) {
        //todo: Обратите внимание:
        // если для события лимит заявок равен 0 или отключена пре-модерация заявок,
        // то подтверждение заявок не требуется
        // нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
        // если при подтверждении данной заявки, лимит заявок для события исчерпан,
        // то все неподтверждённые заявки необходимо отклонить
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Request request = requestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException("Такой заявки c id " + reqId + " нет"));
        request.setStatus(Status.CONFIRMED);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectedRequestByUser(Long userId, Long eventId, Long reqId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Request request = requestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException("Такой заявки c id " + reqId + " нет"));
        request.setStatus(Status.REJECTED);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public List<EventFullDto> findAllEventsByParams(
            List<Long> users, List<String> states, List<Long> categories,
            String rangeStart, String rangeEnd, Long from, Long size) {
        List<Status> statusList = getStatusFromString(states);
        LocalDateTime start = LocalDateTime.parse(rangeStart,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = LocalDateTime.parse(rangeEnd,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Event> events = eventRepository
                .findAllByInitiator_IdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
                        users, statusList, categories, start, end);
        return EventMapper.toEventFullDtoList(events);
    }

    private List<Status> getStatusFromString(List<String> state) {
        List<Status> statusList = new ArrayList<>();
        for (String entry : state) {
            statusList.add(Status.valueOf(entry));
        }
        return statusList;
    }

    @Override
    public EventFullDto updateEventById(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        event.setAnnotation(adminUpdateEventRequest.getAnnotation());

        Long categoryId = adminUpdateEventRequest.getCategory();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + categoryId + " нет"));
        event.setCategory(event.getCategory());

        event.setDescription(adminUpdateEventRequest.getDescription());
        event.setEventDate(LocalDateTime.parse(adminUpdateEventRequest.getEventDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        event.setLocation(adminUpdateEventRequest.getLocation());
        event.setPaid(adminUpdateEventRequest.getPaid());
        event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
        event.setRequestModeration(adminUpdateEventRequest.getRequestModeration());
        event.setTitle(adminUpdateEventRequest.getTitle());

        eventRepository.save(event);
        EventFullDto eventFullDto;
        if (event.getState().equals(Status.PUBLISHED)) {
            eventFullDto = EventMapper.toPublishedEventFullDto(event, category);
        } else {
            eventFullDto = EventMapper.toEventFullDto(event, category);
        }
        return eventFullDto;
    }

    @Override
    public EventFullDto publishedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        event.setState(Status.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        eventRepository.save(event);
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        return EventMapper.toPublishedEventFullDto(event, category);
    }

    @Override
    public EventFullDto rejectedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        event.setState(Status.CANCELED);
        eventRepository.save(event);
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        return EventMapper.toEventFullDto(event, category);
    }

}
