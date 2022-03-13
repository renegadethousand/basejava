package com.urise.webapp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(oddOrEven(List.of(1, 2, 3, 3, 2, 3)));
        System.out.println(oddOrEven(List.of(8, 9)));
    }

    static int minValue(int[] values) {
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, item) -> acc * 10 + item);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(item -> item % 2 == 0));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
