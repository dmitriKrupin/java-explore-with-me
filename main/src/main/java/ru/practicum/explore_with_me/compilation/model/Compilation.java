package ru.practicum.explore_with_me.compilation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "compilations", schema = "public")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean pinned;
    //@ManyToMany(cascade = {CascadeType.ALL})
    //@JoinTable(
    //        name = "event_compilations",
    //        joinColumns = {@JoinColumn(name = "event_id")},
    //        inverseJoinColumns = {@JoinColumn(name = "compilation_id")}
    //)
    //private Set<Event> events = new HashSet<>();
    @ElementCollection
    @CollectionTable(name = "events_list", joinColumns = @JoinColumn(name = "compilation_id"))
    private List<Long> events; //todo: как быть с таблицей events_list? список id событий нужно как-то хранить.

    public Compilation(String title, Boolean pinned, List<Long> events) {
        this.title = title;
        this.pinned = pinned;
        this.events = events;
    }
}
