package org.mapstream;

import java.util.stream.Stream;

public interface PairEntryStream<K, V> extends Stream<PairEntry<K, V>> {
    MapStream<K, V> mapStream();
}
