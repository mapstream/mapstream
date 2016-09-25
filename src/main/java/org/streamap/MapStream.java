package org.streamap;


import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface MapStream<K, V> {

    static <K1, V1> MapStream<K1, V1> mapStream(Stream<? extends Entry<K1, V1>> mapStream) {
        return new MapStreamImpl<>(mapStream);
    }

    static <K1, V1> MapStream<K1, V1> mapStream(Map<K1, V1> map) {
        return new MapStreamImpl<>(map);
    }

    PairEntryStream<K, V> toPairStream();

    Stream<K> toKeyStream();

    Stream<V> toValueStream();


    <K2, V2> MapStream<K2, V2> map(BiFunction<? super K, ? super V, ? extends PairEntry<K2, V2>> mapper);

    <K2, V2> MapStream<K2, V2> map(Function<? super PairEntry<K, V>, ? extends PairEntry<K2, V2>> mapper);

    <K2> MapStream<K2, V> mapKey(BiFunction<? super K, ? super V, ? extends K2> mapper);

    <K2> MapStream<K2, V> mapKey(Function<? super K, ? extends K2> mapper);

    <V2> MapStream<K, V2> mapValue(BiFunction<? super K, ? super V, ? extends V2> mapper);

    <V2> MapStream<K, V2> mapValue(Function<? super V, ? extends V2> mapper);

    MapStream<K, V> filter(BiPredicate<? super K, ? super V> predicate);

    MapStream<K, V> filterByKey(Predicate<? super K> predicate);

    MapStream<K, V> filterByValue(Predicate<? super V> predicate);

    MapStream<K, V> peek(BiConsumer<? super K, ? super V> consumer);

    MapStream<K, V> peekKey(Consumer<? super K> consumer);

    MapStream<K, V> peekValue(Consumer<? super V> consumer);

    MapStream<K, V> flatMapValue(BiFunction<? super K, ? super V, ? extends Stream<V>> mapper);

    MapStream<K, V> distinct();

    long count();

    boolean allMatch(BiPredicate<? super K, ? super V> predicate);

    boolean allValuesMatch(Predicate<? super V> predicate);

    boolean allKeysMatch(Predicate<? super K> predicate);

    boolean anyMatch(BiPredicate<? super K, ? super V> predicate);

    boolean anyValuesMatch(Predicate<? super V> predicate);

    boolean anyKeysMatch(Predicate<? super K> predicate);

    boolean noneMatch(BiPredicate<? super K, ? super V> predicate);

    boolean noneValuesMatch(Predicate<? super V> predicate);

    boolean noneKeysMatch(Predicate<? super K> predicate);

    Optional<PairEntry<K, V>> min(Comparator<? super V> valueComparator);

    Optional<V> minValue(Comparator<? super V> valueComparator);

    Optional<PairEntry<K, V>> max(Comparator<? super V> valueComparator);

    Optional<V> maxValue(Comparator<? super V> valueComparator);

    Optional<V> findAnyValue();

    Optional<K> findAnyKey();

    Optional<PairEntry<K, V>> findAny();

    void forEach(BiConsumer<? super K, ? super V> consumer);


    <R, A> R collect(Collector<? super PairEntry<K, V>, A, R> collector);


    MapWithMapStream<K, V> toMap();

    Set<K> toKeySet();

    Set<V> toValueSet();


    // TODO: add reduce


    // TODO:
    /*    <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super PairEntry<K, V>> accumulator,
                  BiConsumer<R, R> combiner);*/
}
