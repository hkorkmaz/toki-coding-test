package com.tokigames.util.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SorterTest {

    @Test
    public void test_filtering() {

        Sorter<MyClass> sorter = new Sorter<>();

        sorter.add("prop1", MyClass::getProp1)
              .add("prop2", MyClass::getProp2);

        MyClass m1 = new MyClass("Class1-Prop1", "Class1-Prop2");
        MyClass m2 = new MyClass("Class2-Prop1", "Class2-Prop2");
        MyClass m3 = new MyClass("Class3-Prop1", "Class3-Prop2");

        SortParams sortParams = new SortParams("prop1", "desc");

        List<MyClass> result = Stream.of(m1, m2, m3)
                .sorted(sorter.sortBy(sortParams))
                .collect(Collectors.toList());

        assertEquals(3, result.size());
        assertEquals("Class3-Prop1", result.get(0).prop1);
        assertEquals("Class2-Prop1", result.get(1).prop1);
        assertEquals("Class1-Prop1", result.get(2).prop1);
    }

    @Test
    public void test_invalid_sorting() {
        Sorter<MyClass> sorter = new Sorter<>();

        sorter.add("prop1", MyClass::getProp1)
                .add("prop2", MyClass::getProp2);

        MyClass m1 = new MyClass("Class1-Prop1", "Class1-Prop2");
        MyClass m2 = new MyClass("Class2-Prop1", "Class2-Prop2");
        MyClass m3 = new MyClass("Class3-Prop1", "Class3-Prop2");

        SortParams sortParams = new SortParams("dummySortField", "desc");

        List<MyClass> result = Stream.of(m1, m2, m3)
                .sorted(sorter.sortBy(sortParams))
                .collect(Collectors.toList());

        assertEquals(3, result.size());
        assertEquals("Class1-Prop1", result.get(0).prop1);
        assertEquals("Class2-Prop1", result.get(1).prop1);
        assertEquals("Class3-Prop1", result.get(2).prop1);
    }

    @Data
    @AllArgsConstructor
    static class MyClass {
        String prop1;
        String prop2;
    }
}