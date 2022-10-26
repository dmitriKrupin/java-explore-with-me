package ru.practicum.explore_with_me.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.model.Stats;

public interface StatRepository extends JpaRepository<Stats, Long> {
}
