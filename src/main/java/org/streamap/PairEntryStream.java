package org.streamap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

public class PairEntryStream<K, V> implements Stream<PairEntry<K, V>> {

    private Stream<PairEntry<K, V>> delegate;

    PairEntryStream(Stream<PairEntry<K, V>> delegate) {
        this.delegate = delegate;
    }

    public MapStream<K, V> toMapStream() {
        return MapStream.mapStream(delegate);
    }


    /* EVERYTHING DELEGATED */

    @Override
    public Stream<PairEntry<K, V>> filter(Predicate<? super PairEntry<K, V>> predicate) {
        return delegate.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super PairEntry<K, V>, ? extends R> mapper) {
        return delegate.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super PairEntry<K, V>> mapper) {
        return delegate.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super PairEntry<K, V>> mapper) {
        return delegate.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super PairEntry<K, V>> mapper) {
        return delegate.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super PairEntry<K, V>, ? extends Stream<? extends R>> mapper) {
        return delegate.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super PairEntry<K, V>, ? extends IntStream> mapper) {
        return delegate.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super PairEntry<K, V>, ? extends LongStream> mapper) {
        return delegate.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super PairEntry<K, V>, ? extends DoubleStream> mapper) {
        return delegate.flatMapToDouble(mapper);
    }

    @Override
    public Stream<PairEntry<K, V>> distinct() {
        return delegate.distinct();
    }

    @Override
    public Stream<PairEntry<K, V>> sorted() {
        return delegate.sorted();
    }

    @Override
    public Stream<PairEntry<K, V>> sorted(Comparator<? super PairEntry<K, V>> comparator) {
        return delegate.sorted(comparator);
    }

    @Override
    public Stream<PairEntry<K, V>> peek(Consumer<? super PairEntry<K, V>> action) {
        return delegate.peek(action);
    }

    @Override
    public Stream<PairEntry<K, V>> limit(long maxSize) {
        return delegate.limit(maxSize);
    }

    @Override
    public Stream<PairEntry<K, V>> skip(long n) {
        return delegate.skip(n);
    }

    @Override
    public void forEach(Consumer<? super PairEntry<K, V>> action) {
        delegate.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super PairEntry<K, V>> action) {
        delegate.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return delegate.toArray(generator);
    }

    @Override
    public PairEntry<K, V> reduce(PairEntry<K, V> identity, BinaryOperator<PairEntry<K, V>> accumulator) {
        return delegate.reduce(identity, accumulator);
    }

    @Override
    public Optional<PairEntry<K, V>> reduce(BinaryOperator<PairEntry<K, V>> accumulator) {
        return delegate.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super PairEntry<K, V>, U> accumulator, BinaryOperator<U> combiner) {
        return delegate.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super PairEntry<K, V>> accumulator, BiConsumer<R, R> combiner) {
        return delegate.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super PairEntry<K, V>, A, R> collector) {
        return delegate.collect(collector);
    }

    @Override
    public Optional<PairEntry<K, V>> min(Comparator<? super PairEntry<K, V>> comparator) {
        return delegate.min(comparator);
    }

    @Override
    public Optional<PairEntry<K, V>> max(Comparator<? super PairEntry<K, V>> comparator) {
        return delegate.max(comparator);
    }

    @Override
    public long count() {
        return delegate.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super PairEntry<K, V>> predicate) {
        return delegate.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super PairEntry<K, V>> predicate) {
        return delegate.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super PairEntry<K, V>> predicate) {
        return delegate.noneMatch(predicate);
    }

    @Override
    public Optional<PairEntry<K, V>> findFirst() {
        return delegate.findFirst();
    }

    @Override
    public Optional<PairEntry<K, V>> findAny() {
        return delegate.findAny();
    }

    public static <T> Builder<T> builder() {
        return Stream.builder();
    }

    public static <T> Stream<T> empty() {
        return Stream.empty();
    }

    public static <T> Stream<T> of(T t) {
        return Stream.of(t);
    }

    @SafeVarargs
    public static <T> Stream<T> of(T... values) {
        return Stream.of(values);
    }

    public static <T> Stream<T> iterate(T seed, UnaryOperator<T> f) {
        return Stream.iterate(seed, f);
    }

    public static <T> Stream<T> generate(Supplier<T> s) {
        return Stream.generate(s);
    }

    public static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b) {
        return Stream.concat(a, b);
    }

    @Override
    public Iterator<PairEntry<K, V>> iterator() {
        return delegate.iterator();
    }

    @Override
    public Spliterator<PairEntry<K, V>> spliterator() {
        return delegate.spliterator();
    }

    @Override
    public boolean isParallel() {
        return delegate.isParallel();
    }

    @Override
    public Stream<PairEntry<K, V>> sequential() {
        return delegate.sequential();
    }

    @Override
    public Stream<PairEntry<K, V>> parallel() {
        return delegate.parallel();
    }

    @Override
    public Stream<PairEntry<K, V>> unordered() {
        return delegate.unordered();
    }

    @Override
    public Stream<PairEntry<K, V>> onClose(Runnable closeHandler) {
        return delegate.onClose(closeHandler);
    }

    @Override
    public void close() {
        delegate.close();
    }


}
