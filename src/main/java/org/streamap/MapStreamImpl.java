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

    private MapStreamImpl(Stream<? extends Entry<K, V>> entryStream, boolean ready) {
        if(ready) {
            stream = (Stream<PairEntry<K, V>>) entryStream;
        } else {
            stream = entryStream.map(PairEntry::of);
        }
    }

    private <K2, V2> MapStreamImpl<K2, V2> next(Stream<PairEntry<K2, V2>> newStream) {
        return new MapStreamImpl<>(newStream, true);
    }

    @Override
    public PairEntryStream<K, V> toPairStream() {
        return new PairEntryStream<>(stream);
    }

    @Override
    public Stream<K> toKeyStream() {
        return stream.map(PairEntry::k);
    }

    @Override
    public Stream<V> toValueStream() {
        return stream.map(PairEntry::v);
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
    public <K2> MapStream<K2, V> mapKey(BiFunction<? super K, ? super V, ? extends K2> mapper) {
        return next(stream.map(x -> x.withKey(mapper.apply(x.k(), x.v()))));
    }

    @Override
    public <K2> MapStream<K2, V> mapKey(Function<? super K, ? extends K2> mapper) {
        return next(stream.map(x -> x.withKey(mapper.apply(x.k()))));
    }

    @Override
    public <V2> MapStream<K, V2> mapValue(BiFunction<? super K, ? super V, ? extends V2> mapper) {
        return next(stream.map(x -> x.withValue(mapper.apply(x.k(), x.v()))));
    }

    @Override
    public <V2> MapStream<K, V2> mapValue(Function<? super V, ? extends V2> mapper) {
        return next(stream.map(x -> x.withValue(mapper.apply(x.v()))));
    }

    @Override
    public MapStream<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
        return next(stream.filter(x -> predicate.test(x.k(), x.v())));
    }

    @Override
    public MapStream<K, V> filterByKey(Predicate<? super K> predicate) {
        return next(stream.filter(x -> predicate.test(x.k())));
    }

    @Override
    public MapStream<K, V> filterByValue(Predicate<? super V> predicate) {
        return next(stream.filter(x -> predicate.test(x.v())));
    }

    @Override
    public MapStream<K, V> peek(BiConsumer<? super K, ? super V> consumer) {
        return next(stream.peek(pair -> consumer.accept(pair.k(), pair.v())));
    }

    @Override
    public MapStream<K, V> peekKey(Consumer<? super K> consumer) {
        return next(stream.peek(pair -> consumer.accept(pair.k())));
    }

    @Override
    public MapStream<K, V> peekValue(Consumer<? super V> consumer) {
        return next(stream.peek(pair -> consumer.accept(pair.v())));
    }

    @Override
    public MapStream<K, V> flatMapValue(BiFunction<? super K, ? super V, ? extends Stream<V>> mapper) {
        return null;
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
    public Optional<V> minValue(Comparator<? super V> valueComparator) {
        return stream.min((pair1, pair2) -> valueComparator.compare(pair1.v(), pair2.v())).map(PairEntry::v);
    }

    @Override
    public Optional<PairEntry<K, V>> max(Comparator<? super V> valueComparator) {
        return stream.max((pair1, pair2) -> valueComparator.compare(pair1.v(), pair2.v()));
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
    public <R, A> R collect(Collector<? super PairEntry<K, V>, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public MapWithMapStream<K, V> toMap() {
        return new MapWithMapStream<>(collect(Collectors.toMap(PairEntry::k, PairEntry::v)));
    }

    @Override
    public Set<K> toKeySet() {
        return stream.map(PairEntry::k).collect(Collectors.toSet());
    }

    @Override
    public Set<V> toValueSet() {
        return stream.map(PairEntry::v).collect(Collectors.toSet());
    }
}
