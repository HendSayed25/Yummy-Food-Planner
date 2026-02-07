package com.example.yummy_food_planner.presentation.shared.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mapper {

    private Mapper(){}

    public static <F, T> T mapToUi(F from, Function<F, T> mapper) {
        return mapper.apply(from);
    }

    public static <F, T> List<T> mapToUiList(List<F> list, Function<F, T> mapper) {
        return list.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}