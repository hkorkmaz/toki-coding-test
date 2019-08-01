package com.tokigames.util.web;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Sorter<T> {

    private Map<String, Comparator<T>> sorters = new HashMap<>();

    public <U extends Comparable<U>> Sorter<T> add(String field, Function<T, U> extractor){
        sorters.put(field, Comparator.comparing(extractor));
        return this;
    }
    
    public Comparator<T> sortBy(SortParams sorting){
        return sortBy(sorting.getSortBy(), sorting.getDir());
    }

    private Comparator<T> sortBy(String sortBy, String direction){
        Comparator<T> noSort = (a, b) -> 0;

        if(direction == null || direction.equalsIgnoreCase("asc"))
            return sorters.getOrDefault(sortBy, noSort);
        else
            return sorters.getOrDefault(sortBy, noSort).reversed();
    }
}
