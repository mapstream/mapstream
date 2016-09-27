package org.streamap;


import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * <p>
 * Traits:
 * <p>
 * <li> never ordered
 */
public interface MapStream<K, V> {

    static <K1, V1> MapStream<K1, V1> mapStream(Stream<? extends Entry<K1, V1>> entryStream) {
        return new MapStreamImpl<>(entryStream);
    }

    static <K1, V1> MapStream<K1, V1> mapStream(Map<K1, V1> map) {
        return new MapStreamImpl<>(map);
    }

    static <K1, V1> MapStream<K1, V1> from(Stream<? extends Entry<K1, V1>> entryStream) {
        return mapStream(entryStream);
    }

    static <K1, V1> MapStream<K1, V1> from(Map<K1, V1> map) {
        return mapStream(map);
    }


    MapStreamablePairStream<K, V> pairStream();

    Stream<K> keyStream();

    Stream<V> valueStream();


    StreamableMap<K, V> toMap();

    StreamableMap<K, V> toMap(BinaryOperator<V> mergeFunction);

    <M extends Map<K, V>> StreamableMap<K, V> toMap(BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier);

    <M extends Map<K, V>> StreamableMap<K, V> toMap(Supplier<M> mapSupplier);


    Set<K> keySet();

    Set<V> valueSet();

    <K2, V2> MapStream<K2, V2> map(Function<? super K, ? extends K2> keyMapper, Function<? super V, ? extends V2> valueMapper);

    <K2, V2> MapStream<K2, V2> map(BiFunction<? super K, ? super V, ? extends K2> keyMapper, BiFunction<? super K, ? super V, ? extends V2> valueMapper);

    <K2, V2> MapStream<K2, V2> map(Function<? super PairEntry<K, V>, ? extends PairEntry<K2, V2>> mapper);

    <K2> MapStream<K2, V> mapKeys(Function<? super K, ? extends K2> mapper);

    <V2> MapStream<K, V2> mapValues(Function<? super V, ? extends V2> mapper);

    <K2, V2> MapStream<K2, V2> map(BiFunction<? super K, ? super V, ? extends PairEntry<K2, V2>> mapper);

    <K2> MapStream<K2, V> mapKeys(BiFunction<? super K, ? super V, ? extends K2> mapper);

    <V2> MapStream<K, V2> mapValues(BiFunction<? super K, ? super V, ? extends V2> mapper);


    <K2, V2> MapStream<K2, V2> flatMap(Function<? super PairEntry<K, V>, ? extends Stream<PairEntry<K2, V2>>> mapper);

    <K2, V2> MapStream<K2, V2> flatMapKeys(Function<? super K, ? extends Stream<PairEntry<K2, V2>>> mapper);

    <K2, V2> MapStream<K2, V2> flatMapValues(Function<? super V, ? extends Stream<PairEntry<K2, V2>>> mapper);


    MapStream<K, V> filter(BiPredicate<? super K, ? super V> predicate);

    MapStream<K, V> filterKeys(Predicate<? super K> predicate);

    MapStream<K, V> filterValues(Predicate<? super V> predicate);


    MapStream<K, V> peek(BiConsumer<? super K, ? super V> consumer);

    MapStream<K, V> peekKeys(Consumer<? super K> consumer);

    MapStream<K, V> peekValues(Consumer<? super V> consumer);


    MapStream<K, V> distinct();


    long count();


    boolean allMatch(BiPredicate<? super K, ? super V> predicate);

    boolean allKeysMatch(Predicate<? super K> predicate);

    boolean allValuesMatch(Predicate<? super V> predicate);


    boolean anyMatch(BiPredicate<? super K, ? super V> predicate);

    boolean anyKeysMatch(Predicate<? super K> predicate);

    boolean anyValuesMatch(Predicate<? super V> predicate);


    boolean noneMatch(BiPredicate<? super K, ? super V> predicate);

    boolean noneKeysMatch(Predicate<? super K> predicate);

    boolean noneValuesMatch(Predicate<? super V> predicate);


    Optional<PairEntry<K, V>> min(Comparator<? super V> valueComparator);

    Optional<K> minKey(Comparator<? super K> keyComparator);

    Optional<V> minValue(Comparator<? super V> valueComparator);


    Optional<PairEntry<K, V>> max(Comparator<? super V> valueComparator);

    Optional<K> maxKey(Comparator<? super K> keyComparator);

    Optional<V> maxValue(Comparator<? super V> valueComparator);


    Optional<PairEntry<K, V>> findAny();

    Optional<K> findAnyKey();

    Optional<V> findAnyValue();


    void forEach(BiConsumer<? super K, ? super V> consumer);

    void forEach(Consumer<? super PairEntry<K, V>> consumer);


    <R, A> R collect(Collector<? super PairEntry<K, V>, A, R> collector);

    <R, A> R collectKey(Collector<? super K, A, R> collector);

    <R, A> R collectValue(Collector<? super V, A, R> collector);


    <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super PairEntry<K, V>> accumulator,
                  BiConsumer<R, R> combiner);

    <R> R collectKey(Supplier<R> supplier,
                     BiConsumer<R, ? super K> accumulator,
                     BiConsumer<R, R> combiner);

    <R> R collectValue(Supplier<R> supplier,
                       BiConsumer<R, ? super V> accumulator,
                       BiConsumer<R, R> combiner);


    PairEntry<K, V> reduce(PairEntry<K, V> identity, BinaryOperator<PairEntry<K, V>> accumulator);

    K reduceKey(K identity, BinaryOperator<K> accumulator);

    V reduceValue(V identity, BinaryOperator<V> accumulator);


    Optional<PairEntry<K, V>> reduce(BinaryOperator<PairEntry<K, V>> accumulator);

    Optional<K> reduceKey(BinaryOperator<K> accumulator);

    Optional<V> reduceValue(BinaryOperator<V> accumulator);


    <U> U reduce(U identity,
                 BiFunction<U, ? super PairEntry<K, V>, U> accumulator,
                 BinaryOperator<U> combiner);


    <U> U reduceKey(U identity,
                    BiFunction<U, ? super K, U> accumulator,
                    BinaryOperator<U> combiner);


    <U> U reduceValue(U identity,
                      BiFunction<U, ? super V, U> accumulator,
                      BinaryOperator<U> combiner);

}
