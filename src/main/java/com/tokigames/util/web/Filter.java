package com.tokigames.util.web;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Filter<T> {

    private Map<String, Function<T, ?>> filters = new HashMap<>();

    public <U> Filter<T> add(String field, Function<T, U> extractor) {
        filters.put(field, extractor);
        return this;
    }

    public Predicate<T> filterBy(FilterParams filterParams) {
        Predicate<T> noFilter = (a) -> true;

        String filterName = filterParams.field();
        Object filterValue = filterParams.value();

        if (filterName != null && filterValue != null && filters.containsKey(filterName)) {
            Function<T, ?> filter = filters.get(filterName);
            return (obj) -> filter.apply(obj).toString().equals(filterValue);
        }

        return noFilter;
    }
}
