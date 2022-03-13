package com.urise.webapp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(oddOrEven(List.of(1, 2, 3, 3, 2, 3)));
    }

    static int minValue(int[] values) {
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, item) -> acc * 10 + item);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        boolean even = integers.stream()
                .reduce(0, Integer::sum) % 2 == 0;
        return integers.stream()
                .filter(item -> {
                    if (even) {
                        return item % 2 != 0;
                    } else {
                        return item % 2 == 0;
                    }
                })
                .collect(Collectors.toList());
    }
}
