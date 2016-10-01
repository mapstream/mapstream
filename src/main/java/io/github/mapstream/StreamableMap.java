package io.github.mapstream;

import java.util.Map;

public interface StreamableMap<K, V> extends Map<K, V> {
    MapStream<K, V> mapStream();
}
