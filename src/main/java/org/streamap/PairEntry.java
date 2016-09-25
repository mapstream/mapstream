package org.streamap;

import java.util.Map;

public class PairEntry<K, V> implements Map.Entry<K, V> {
    private final K key;
    private V value;

    private PairEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> PairEntry<K, V> of(K key, V value) {
        return new PairEntry<>(key, value);
    }

    public static <K, V> PairEntry<K, V> of(Map.Entry<K, V> entry) {
        return of(entry.getKey(), entry.getValue());
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V newValue) {
        value = newValue;
        return value;
    }

    <K2> PairEntry<K2, V> withKey(K2 key) {
        return PairEntry.of(key, v());
    }

    <V2> PairEntry<K, V2> withValue(V2 value) {
        return PairEntry.of(k(), value);
    }

    public K k() { return getKey(); }
    public V v() { return getValue(); }

    public PairEntry<V, K> swap() {
        return new PairEntry<>(value, key);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PairEntry<?, ?> pairEntry = (PairEntry<?, ?>) o;

        if (key != null ? !key.equals(pairEntry.key) : pairEntry.key != null)
            return false;
        return value != null ? value.equals(pairEntry.value) : pairEntry.value == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
