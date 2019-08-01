package com.tokigames.util.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FilterTest {

    @Test
    public void test_filtering() {

        Filter<MyClass> filter = new Filter<>();

        filter.add("prop1", MyClass::getProp1)
              .add("prop2", MyClass::getProp2);

        MyClass m1 = new MyClass("Class1-Prop1", "Class1-Prop2");
        MyClass m2 = new MyClass("Class2-Prop1", "Class2-Prop2");
        MyClass m3 = new MyClass("Class3-Prop1", "Class3-Prop2");

        FilterParams filterParams = new FilterParams("prop1:Class2-Prop1");

        List<MyClass> result = Stream.of(m1, m2, m3)
                .filter(filter.filterBy(filterParams))
                .collect(Collectors.toList());

        assertEquals(1, result.size());
        assertEquals("Class2-Prop1", result.get(0).prop1);
        assertEquals("Class2-Prop2", result.get(0).prop2);
    }

    @Test
    public void test_invalid_filtering() {

        Filter<MyClass> filter = new Filter<>();

        filter.add("prop1", MyClass::getProp1)
              .add("prop2", MyClass::getProp2);

        MyClass m1 = new MyClass("Class1-Prop1", "Class1-Prop2");
        MyClass m2 = new MyClass("Class2-Prop1", "Class2-Prop2");
        MyClass m3 = new MyClass("Class3-Prop1", "Class3-Prop2");

        FilterParams filterParams = new FilterParams("dummyFilter:Class2-Prop1");

        List<MyClass> result = Stream.of(m1, m2, m3)
                .filter(filter.filterBy(filterParams))
                .collect(Collectors.toList());

        assertEquals(3, result.size());
    }

    @Data
    @AllArgsConstructor
    static class MyClass {
        String prop1;
        String prop2;
    }
}