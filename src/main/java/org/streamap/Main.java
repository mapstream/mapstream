package org.streamap;

import java.util.HashMap;
import java.util.Map;

import static org.streamap.MapStream.mapStream;

class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> intMap = new HashMap<>();

        intMap.put(10, 10);
        intMap.put(20, 50);

        Map<Integer, Integer> map = mapStream(intMap)
                .mapKeys(x -> x * 20)
                .mapValues((key, value) -> value * 50)
                .toMap()
                .mapStream()
                .pairStream()
                .mapStream()
                .pairStream()
                .mapStream()
                .pairStream()
                .mapStream()
                .pairStream()
                .mapStream()
                .toMap();

        System.out.println(map);

    }
}
