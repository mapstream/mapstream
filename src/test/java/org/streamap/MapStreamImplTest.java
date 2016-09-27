package org.streamap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.streamap.MapStream.mapStream;
import static org.streamap.PairEntry.pair;

public class MapStreamImplTest {

    Map<Integer, Integer> fullMap;
    MapStream<Integer, Integer> fullStream;
    Map<Integer, Integer> emptyMap;
    MapStream<Integer, Integer> emptyStream;

    @Before
    public void setUp() throws Exception {
        fullMap = new HashMap<>();
        fullMap.put(1, 2);
        fullMap.put(3, 500);
        fullMap.put(5, 100);
        fullMap.put(10, 10);
        fullMap.put(-10, 50);
        fullStream = mapStream(fullMap);

        emptyMap = new HashMap<>();
        emptyStream = mapStream(emptyMap);
    }

    @Test
    public void keyStream() throws Exception {

        // when
        long emptyCount = emptyStream.keyStream().count();
        Set<Integer> fullKeySet = fullStream.keyStream().collect(toSet());

        // then
        assertEquals(emptyCount, 0);
        assertEquals(fullKeySet, fullMap.keySet());

    }

    @Test
    public void valueStream() throws Exception {
        // when
        long emptyCount = emptyStream.keyStream().count();
        Set<Integer> fullValueSet = fullStream.valueStream().collect(toSet());

        // then
        assertEquals(emptyCount, 0);
        assertEquals(fullValueSet, new HashSet<>(fullMap.values()));

    }

    @Test
    public void map() throws Exception {
        // when
        Map<Integer, Integer> mapped = Map(fullStream.map((key, value) -> pair(key * 10, value * 20)));
        Map<Integer, Integer> expected = mapOf(
                10, 40,
                30, 10000,
                50, 2000,
                100, 200,
                -100, 1000
        );

        // then
        assertEquals(expected, mapped);
    }

    @Test(expected = IllegalStateException.class)
    public void testMapWithKeyCollisionWithException() throws Exception {
        // given
        Map<Integer, Integer> expected = mapOf(
                1, 10000
        );

        // when
        Map<Integer, Integer> mapped = fullStream.map((key, value) -> pair(1, value * 20)).toMap();

        // then throw exception
    }

    @Test
    public void testMapWithKeyCollision() throws Exception {
        // given
        Map<Integer, Integer> expected = mapOf(
                1, 10000
        );

        // when
        Map<Integer, Integer> mapped = fullStream.map((key, value) -> pair(1, value * 20)).toMap((x, y) -> x > y ? x : y);


        // then
        assertEquals(expected, mapped);
    }

    @Test
    public void mapKeys() throws Exception {

    }

    @Test
    public void mapKeys1() throws Exception {

    }

    @Test
    public void mapValues() throws Exception {

    }

    @Test
    public void flatMap() throws Exception {

    }

    @Test
    public void flatMapKeys() throws Exception {

    }

    @Test
    public void flatMapValues() throws Exception {

    }

    @Test
    public void mapValues1() throws Exception {

    }

    @Test
    public void filter() throws Exception {

    }

    @Test
    public void filterKeys() throws Exception {

    }

    @Test
    public void filterValues() throws Exception {

    }

    private void peekPairs(Map<Integer, Integer> map) throws Exception {
        // given
        Set<Pair<Integer, Integer>> expected = map.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())).collect(toSet());
        Set<Pair<Integer, Integer>> actualCalledWith = new HashSet<>();

        // when
        consume(mapStream(map).peek((k, v) -> actualCalledWith.add(Pair.of(k, v))));

        // then
        assertEquals(expected, actualCalledWith);
    }


    @Test
    public void peekPairsFull() throws Exception {
        peekPairs(fullMap);
    }

    @Test
    public void peekPairsEmpty() throws Exception {
        peekPairs(emptyMap);
    }

    private void peekKeys(Map<Integer, Integer> map) throws Exception {
        // given
        Set<Integer> expected = map.keySet();
        Set<Integer> actualCalledWith = new HashSet<>();

        // when
        consume(mapStream(map).peekKeys(actualCalledWith::add));

        // then
        assertEquals(expected, actualCalledWith);
    }

    @Test
    public void peekKeysFull() throws Exception {
        peekKeys(fullMap);
    }

    @Test
    public void peekKeysEmpty() throws Exception {
        peekKeys(emptyMap);
    }

    private void peekValues(Map<Integer, Integer> map) throws Exception {
        // given
        Set<Integer> expected = new HashSet<>(map.values());
        Set<Integer> actualCalledWith = new HashSet<>();

        // when
        consume(mapStream(map).peekValues(actualCalledWith::add));

        // then
        assertEquals(expected, actualCalledWith);
    }

    @Test
    public void peekValuesFull() throws Exception {
        peekValues(fullMap);
    }

    @Test
    public void peekValuesEmpty() throws Exception {
        peekValues(emptyMap);
    }

    @Test
    public void distinct() throws Exception {

    }

    @Test
    public void count() throws Exception {

    }

    @Test
    public void allMatch() throws Exception {

    }

    @Test
    public void allValuesMatch() throws Exception {

    }

    @Test
    public void allKeysMatch() throws Exception {

    }

    @Test
    public void anyMatch() throws Exception {

    }

    @Test
    public void anyValuesMatch() throws Exception {

    }

    @Test
    public void anyKeysMatch() throws Exception {

    }

    @Test
    public void noneMatch() throws Exception {

    }

    @Test
    public void noneValuesMatch() throws Exception {

    }

    @Test
    public void noneKeysMatch() throws Exception {

    }

    @Test
    public void min() throws Exception {

    }

    @Test
    public void minKey() throws Exception {

    }

    @Test
    public void minValue() throws Exception {

    }

    @Test
    public void max() throws Exception {

    }

    @Test
    public void maxKey() throws Exception {

    }

    @Test
    public void maxValue() throws Exception {

    }

    @Test
    public void findAnyValue() throws Exception {

    }

    @Test
    public void findAnyKey() throws Exception {

    }

    @Test
    public void findAny() throws Exception {

    }

    @Test
    public void forEach() throws Exception {

    }

    @Test
    public void forEach1() throws Exception {

    }

    @Test
    public void collect() throws Exception {

    }

    @Test
    public void collectKey() throws Exception {

    }

    @Test
    public void collectValue() throws Exception {

    }

    @Test
    public void collect1() throws Exception {

    }

    @Test
    public void collectKey1() throws Exception {

    }

    @Test
    public void collectValue1() throws Exception {

    }

    @Test
    public void reduce() throws Exception {

    }

    @Test
    public void reduceKey() throws Exception {

    }

    @Test
    public void reduceValue() throws Exception {

    }

    @Test
    public void reduce1() throws Exception {

    }

    @Test
    public void reduceKey1() throws Exception {

    }

    @Test
    public void reduceValue1() throws Exception {

    }

    @Test
    public void reduce2() throws Exception {

    }

    @Test
    public void reduceKey2() throws Exception {

    }

    @Test
    public void reduceValue2() throws Exception {

    }

    @Test
    public void toMap() throws Exception {

    }

    @Test
    public void keySet() throws Exception {

    }

    @Test
    public void valueSet() throws Exception {

    }

    private <K, V> void consume(MapStream<K, V> stream) {
        stream.collect(Collectors.counting());
    }

    private static <T> Map<T, T> mapOf(T... elements) {
        if (elements.length % 2 != 0) {
            throw new IllegalArgumentException("elements must be even so that map (from key->value pairs) can be built");
        }

        Map<T, T> map = new HashMap<>();

        for (int i = 0; i < elements.length - 1; i += 2) {
            map.put(elements[i], elements[i + 1]);
        }

        return map;
    }

    private static <K, V> Map<K, V> Map(MapStream<K, V> map) {
        return map.pairStream().collect(Collectors.toMap(PairEntry::k, PairEntry::v));
    }

    private static <K, V> Set<PairEntry<K, V>> Set(MapStream<K, V> map) {
        return map.pairStream().collect(toSet());
    }

    private static <K> Set<K> KeySet(MapStream<K, ?> map) {
        return map.pairStream().map(PairEntry::k).collect(toSet());
    }

    private static <V> Set<V> ValueSet(MapStream<?, V> map) {
        return map.pairStream().map(PairEntry::v).collect(toSet());
    }

}