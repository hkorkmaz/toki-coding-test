package com.tokigames.util.web;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Sorter<T> {

    private Map<String, Comparator<T>> sorters = new HashMap<>();

    public Sorter<T> add(String field, Comparator<T> comparator){
        sorters.put(field, comparator);
        return this;
    }

    public Comparator<T> sortBy(String sortBy, String direction){
        Comparator<T> noSort = (a, b) -> 0;

        if(direction == null || direction.equalsIgnoreCase("asc"))
            return sorters.getOrDefault(sortBy, noSort);
        else
            return sorters.getOrDefault(sortBy, noSort).reversed();
    }

    public Comparator<T> sortBy(SortParams sorting){
        return sortBy(sorting.getSortBy(), sorting.getDir());
    }
}
