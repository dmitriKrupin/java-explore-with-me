package ru.practicum.explore_with_me.event.model;

public enum Status {
    PENDING, //Ожидание публикации.
    // В статус ожидания публикации событие переходит сразу после создания.
    PUBLISHED, //Публикация. В это состояние событие переводит администратор.
    CONFIRMED,
    REJECTED,
    CANCELED //Отмена публикации. В это состояние событие переходит в двух случаях.
    // Первый — если администратор решил, что его нельзя публиковать.
    // Второй — когда инициатор события решил отменить его на этапе ожидания публикации.
}
