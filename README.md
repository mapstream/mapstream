[![Build Status](https://travis-ci.org/mapstream/mapstream.svg?branch=master)](https://travis-ci.org/mapstream/mapstream) [![Coverage Status](https://coveralls.io/repos/github/mapstream/mapstream/badge.svg?branch=master)](https://coveralls.io/github/mapstream/mapstream?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.mapstream/mapstream/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.mapstream/mapstream) [![Gitter](https://img.shields.io/gitter/room/mapstream/mapstream.svg?maxAge=2592000)](https://gitter.im/mapstream/mapstream) 

# mapstream :ocean:

Better stream api for `Map` (Java 8).

### Examples

With mapstream you can do:
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
    map.stream()
        .filter(entry -> !entry.getKey().equals("ignore") && entry.getValue() % 2 == 0)
        .collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue() / 2
        )); // = ["ten" -> 5]
```

### Download

```xml
<dependency>
  <groupId>io.github.mapstream</groupId>
  <artifactId>mapstream</artifactId>
  <version>0.0.1</version>
</dependency>
```

### Implementation


Under the hood it uses `Stream<PairEntry<K, V>>` as a delegate, so you can for example use `MapStream.parallel()` and it will reuse well-tested jvm implementation of parallel stream (`Stream.parallel()`).    

