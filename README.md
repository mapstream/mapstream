[![Build Status](https://travis-ci.org/myhau/streamap.svg?branch=master)](https://travis-ci.org/myhau/streamap) [![codecov](https://codecov.io/gh/myhau/streamap/branch/master/graph/badge.svg)](https://codecov.io/gh/myhau/streamap)

# mapstream 🌊

Better stream api for `Map` (Java 8).

### Examples


```java
Map<String, Integer> map; // = ["five" -> 5, "one" -> 1, "ten" -> 10, "ignore" -> -10]  
Map<String, Integer> dividedByTwo = 
    MapStream.from(map)
        .filterKeys(name -> !name.equals("ignore"))
        .filterValues(number -> number % 2 == 0)
        .mapValues(number -> number / 2)
        .toMap(); // = ["ten" -> 5]
```

instead of 

```java
Map<String, Integer> map; // = ["five" -> 5, "one" -> 1, "ten" -> 10, "ignore" -> -10]
Map<String, Integer> dividedByTwo = 
    MapStream.from(map)
        .filter(entry -> {
            return !entry.getKey().equals("ignore") && entry.getValue() % 2 == 0;
        })
        .collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue() / 2
        )); // = ["ten" -> 5]
```

### Implementation


Under the hood it uses `Stream<PairEntry<K, V>>` as a delegate, so you can for example use `MapStream.parallel()` and it will reuse well-tested jvm implementation of parallel stream (`Stream.parallel()`).    

