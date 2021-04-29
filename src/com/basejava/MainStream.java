package com.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 3, 3};
        System.out.println(minValue(array));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy((n) -> n % 2 == 0));

        //If the number of elements in the collection is odd, then the whole sum is odd -
        //Odd + odd = even
        return map.get(map.get(false).size() % 2 != 0);
    }
}
