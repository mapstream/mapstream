package org.streamap;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MapStreamImpl<K, V> implements MapStream<K, V> {

    private final Stream<PairEntry<K, V>> stream;

    MapStreamImpl(Map<K, V> from) {
        this(from.entrySet().stream());
    }

    MapStreamImpl(Stream<? extends Entry<K, V>> entryStream) {
        this(entryStream, false);
    }

    private MapStreamImpl(Stream<? extends Entry<K, V>> entryStream, boolean mappedBefore) {
        if(mappedBefore) {
            stream = (Stream<PairEntry<K, V>>) entryStream;
        } else {
            // TODO: do we need this ? (performance) (hint: to refactor use inline) (maybe lazy?)
            stream = entryStream.map(PairEntry::of);
        }
    }

    private <K2, V2> MapStreamImpl<K2, V2> next(Stream<PairEntry<K2, V2>> newStream) {
        return new MapStreamImpl<>(newStream, true);
    }

    @Override
    public MapStreamablePairStream<K, V> pairStream() {
        return new MapStreamablePairStream<>(stream);
    }

    @Override
    public Stream<K> keyStream() {
        return stream.map(PairEntry::k);
    }

    @Override
    public Stream<V> valueStream() {
        return stream.map(PairEntry::v);
    }

    @Override
    public <K2, V2> MapStream<K2, V2> map(Function<? super K, ? extends K2> keyMapper, Function<? super V, ? extends V2> valueMapper) {
        return map((k, v) -> PairEntry.of(keyMapper.apply(k), valueMapper.apply(v)));
    }

    @Override
    public <K2, V2> MapStream<K2, V2> map(BiFunction<? super K, ? super V, ? extends K2> keyMapper, BiFunction<? super K, ? super V, ? extends V2> valueMapper) {
        return map((k, v) -> PairEntry.of(keyMapper.apply(k, v), valueMapper.apply(k, v)));
    }

    @Override
    public <K2, V2> MapStream<K2, V2> map(BiFunction<? super K, ? super V, ? extends PairEntry<K2, V2>> mapper) {
        return next(stream.map(x -> mapper.apply(x.k(), x.v())));
    }

    @Override
    public <K2, V2> MapStream<K2, V2> map(Function<? super PairEntry<K, V>, ? extends PairEntry<K2, V2>> mapper) {
        return next(stream.map(mapper));
    }

    @Override
    public <K2> MapStream<K2, V> mapKeys(BiFunction<? super K, ? super V, ? extends K2> mapper) {
        return next(stream.map(x -> x.withKey(mapper.apply(x.k(), x.v()))));
    }

    @Override
    public <K2> MapStream<K2, V> mapKeys(Function<? super K, ? extends K2> mapper) {
        return next(stream.map(x -> x.withKey(mapper.apply(x.k()))));
    }

    @Override
    public <V2> MapStream<K, V2> mapValues(BiFunction<? super K, ? super V, ? extends V2> mapper) {
        return next(stream.map(x -> x.withValue(mapper.apply(x.k(), x.v()))));
    }

    @Override
    public <K2, V2> MapStream<K2, V2> flatMap(Function<? super PairEntry<K, V>, ? extends Stream<PairEntry<K2, V2>>> mapper) {
        return next(stream.flatMap(mapper));
    }

    @Override
    public <K2, V2> MapStream<K2, V2> flatMapKeys(Function<? super K, ? extends Stream<PairEntry<K2, V2>>> mapper) {
        return next(stream.flatMap(pair -> mapper.apply(pair.k())));
    }

    @Override
    public <K2, V2> MapStream<K2, V2> flatMapValues(Function<? super V, ? extends Stream<PairEntry<K2, V2>>> mapper) {
        return next(stream.flatMap(pair -> mapper.apply(pair.v())));
    }

    @Override
    public <V2> MapStream<K, V2> mapValues(Function<? super V, ? extends V2> mapper) {
        return next(stream.map(x -> x.withValue(mapper.apply(x.v()))));
    }

    @Override
    public MapStream<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
        return next(stream.filter(x -> predicate.test(x.k(), x.v())));
    }

    @Override
    public MapStream<K, V> filterKeys(Predicate<? super K> predicate) {
        return next(stream.filter(x -> predicate.test(x.k())));
    }

    @Override
    public MapStream<K, V> filterValues(Predicate<? super V> predicate) {
        return next(stream.filter(x -> predicate.test(x.v())));
    }

    @Override
    public MapStream<K, V> peek(BiConsumer<? super K, ? super V> consumer) {
        return next(stream.peek(pair -> consumer.accept(pair.k(), pair.v())));
    }

    @Override
    public MapStream<K, V> peekKeys(Consumer<? super K> consumer) {
        return next(stream.peek(pair -> consumer.accept(pair.k())));
    }

    @Override
    public MapStream<K, V> peekValues(Consumer<? super V> consumer) {
        return next(stream.peek(pair -> consumer.accept(pair.v())));
    }

    @Override
    public MapStream<K, V> distinct() {
        return next(stream.distinct());
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean allMatch(BiPredicate<? super K, ? super V> predicate) {
        return stream.allMatch(pair -> predicate.test(pair.k(), pair.v()));
    }

    @Override
    public boolean allValuesMatch(Predicate<? super V> predicate) {
        return stream.allMatch(pair -> predicate.test(pair.v()));
    }

    @Override
    public boolean allKeysMatch(Predicate<? super K> predicate) {
        return stream.allMatch(pair -> predicate.test(pair.k()));
    }

    @Override
    public boolean anyMatch(BiPredicate<? super K, ? super V> predicate) {
        return stream.anyMatch(pair -> predicate.test(pair.k(), pair.v()));
    }

    @Override
    public boolean anyValuesMatch(Predicate<? super V> predicate) {
        return stream.anyMatch(pair -> predicate.test(pair.v()));
    }

    @Override
    public boolean anyKeysMatch(Predicate<? super K> predicate) {
        return stream.anyMatch(pair -> predicate.test(pair.k()));
    }

    @Override
    public boolean noneMatch(BiPredicate<? super K, ? super V> predicate) {
        return stream.noneMatch(pair -> predicate.test(pair.k(), pair.v()));
    }

    @Override
    public boolean noneValuesMatch(Predicate<? super V> predicate) {
        return stream.noneMatch(pair -> predicate.test(pair.v()));
    }

    @Override
    public boolean noneKeysMatch(Predicate<? super K> predicate) {
        return stream.noneMatch(pair -> predicate.test(pair.k()));
    }

    @Override
    public Optional<PairEntry<K, V>> min(Comparator<? super V> valueComparator) {
        return stream.min((pair1, pair2) -> valueComparator.compare(pair1.v(), pair2.v()));
    }

    @Override
    public Optional<K> minKey(Comparator<? super K> keyComparator) {
        return stream.min((pair1, pair2) -> keyComparator.compare(pair1.k(), pair2.k())).map(PairEntry::k);
    }

    @Override
    public Optional<V> minValue(Comparator<? super V> valueComparator) {
        return stream.min((pair1, pair2) -> valueComparator.compare(pair1.v(), pair2.v())).map(PairEntry::v);
    }

    @Override
    public Optional<PairEntry<K, V>> max(Comparator<? super V> valueComparator) {
        return stream.max((pair1, pair2) -> valueComparator.compare(pair1.v(), pair2.v()));
    }

    @Override
    public Optional<K> maxKey(Comparator<? super K> keyComparator) {
        return stream.max((pair1, pair2) -> keyComparator.compare(pair1.k(), pair2.k())).map(PairEntry::k);
    }

    @Override
    public Optional<V> maxValue(Comparator<? super V> valueComparator) {
        return stream.max((pair1, pair2) -> valueComparator.compare(pair1.v(), pair2.v())).map(PairEntry::v);
    }

    @Override
    public Optional<V> findAnyValue() {
        return stream.findAny().map(PairEntry::v);
    }

    @Override
    public Optional<K> findAnyKey() {
        return stream.findAny().map(PairEntry::k);
    }

    @Override
    public Optional<PairEntry<K, V>> findAny() {
        return stream.findAny();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> consumer) {
        stream.forEach(pair -> consumer.accept(pair.k(), pair.v()));
    }

    @Override
    public void forEach(Consumer<? super PairEntry<K, V>> consumer) {

    }

    @Override
    public <R, A> R collect(Collector<? super PairEntry<K, V>, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public <R, A> R collectKey(Collector<? super K, A, R> collector) {
        return stream.map(PairEntry::k).collect(collector);
    }

    @Override
    public <R, A> R collectValue(Collector<? super V, A, R> collector) {
        return stream.map(PairEntry::v).collect(collector);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super PairEntry<K, V>> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R> R collectKey(Supplier<R> supplier, BiConsumer<R, ? super K> accumulator, BiConsumer<R, R> combiner) {
        return stream.map(PairEntry::k).collect(supplier, accumulator, combiner);
    }

    @Override
    public <R> R collectValue(Supplier<R> supplier, BiConsumer<R, ? super V> accumulator, BiConsumer<R, R> combiner) {
        return stream.map(PairEntry::v).collect(supplier, accumulator, combiner);
    }

    @Override
    public PairEntry<K, V> reduce(PairEntry<K, V> identity, BinaryOperator<PairEntry<K, V>> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public K reduceKey(K identity, BinaryOperator<K> accumulator) {
        return stream.map(PairEntry::k).reduce(identity, accumulator);
    }

    @Override
    public V reduceValue(V identity, BinaryOperator<V> accumulator) {
        return stream.map(PairEntry::v).reduce(identity, accumulator);
    }

    @Override
    public Optional<PairEntry<K, V>> reduce(BinaryOperator<PairEntry<K, V>> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public Optional<K> reduceKey(BinaryOperator<K> accumulator) {
        return stream.map(PairEntry::k).reduce(accumulator);
    }

    @Override
    public Optional<V> reduceValue(BinaryOperator<V> accumulator) {
        return stream.map(PairEntry::v).reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super PairEntry<K, V>, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <U> U reduceKey(U identity, BiFunction<U, ? super K, U> accumulator, BinaryOperator<U> combiner) {
        return stream.map(PairEntry::k).reduce(identity, accumulator, combiner);
    }

    @Override
    public <U> U reduceValue(U identity, BiFunction<U, ? super V, U> accumulator, BinaryOperator<U> combiner) {
        return stream.map(PairEntry::v).reduce(identity, accumulator, combiner);
    }

    @Override
    public MapStream<K, V> parallel() {
        return next(stream.parallel());
    }

    @Override
    public MapStream<K, V> sequential() {
        return next(stream.sequential());
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public StreamableMap<K, V> toMap() {
        return new StreamableMap<>(collect(Collectors.toMap(PairEntry::k, PairEntry::v)));
    }

    @Override
    public StreamableMap<K, V> toMap(BinaryOperator<V> mergeFunction) {
        return new StreamableMap<>(collect(Collectors.toMap(PairEntry::k, PairEntry::v, mergeFunction)));
    }

    @Override
    public <M extends Map<K, V>> StreamableMap<K, V> toMap(BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier) {
        return new StreamableMap<>(collect(Collectors.toMap(PairEntry::k, PairEntry::v, mergeFunction, mapSupplier)));
    }

    @Override
    public <M extends Map<K, V>> StreamableMap<K, V> toMap(Supplier<M> mapSupplier) {
        return new StreamableMap<>(collect(Collectors.toMap(PairEntry::k, PairEntry::v, null, mapSupplier)));
    }

    @Override
    public Set<K> keySet() {
        return stream.map(PairEntry::k).collect(Collectors.toSet());
    }

    @Override
    public Set<V> valueSet() {
        return stream.map(PairEntry::v).collect(Collectors.toSet());
    }


}
