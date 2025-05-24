package org.example.eventmanagementsystem.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.eventmanagementsystem.model.Event;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Event> search(String name, String date, String category, String location, BigDecimal minPrice, BigDecimal maxPrice) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        Join<?, ?> venueJoin = eventRoot.join("venue");
        Join<?, ?> categoryJoin = eventRoot.join("category");
        Join<?, ?> pricePeriods = eventRoot.join("pricePeriods", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(eventRoot.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (date != null) {
            predicates.add(cb.lessThanOrEqualTo(eventRoot.get("date"), date));
        }
        if (category != null && !category.isEmpty()) {
            predicates.add(cb.equal(categoryJoin.get("name"), category));
        }
        if (location != null && !location.isEmpty()) {
            predicates.add(cb.equal(venueJoin.get("location"), location));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(pricePeriods.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(pricePeriods.get("price"), maxPrice));
        }
        query.distinct(true);
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
