package ru.practicum.ewm.compilation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "compilations", schema = "public")
@Getter
@Setter
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean pinned;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "events_list",
            joinColumns = {@JoinColumn(name = "events_id")},
            inverseJoinColumns = {@JoinColumn(name = "compilation_id")}
    )
    private List<Event> events;

    public Compilation(String title, Boolean pinned, List<Event> events) {
        this.title = title;
        this.pinned = pinned;
        this.events = events;
    }
}
