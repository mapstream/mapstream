[![Build Status](https://travis-ci.org/myhau/streamap.svg?branch=master)](https://travis-ci.org/myhau/streamap) [![codecov](https://codecov.io/gh/myhau/streamap/branch/master/graph/badge.svg)](https://codecov.io/gh/myhau/streamap)

# Streamap

Better streaming api for maps in Java 8.

##### Example


```java
Map<String, Integer> map; // = ["five" -> 5, "one" -> 1, "ten" -> 10, "ignore" -> -10]  
Map<String, Integer> dividedByTwo = 
    mapStream(map)
        .filterKeys(name -> !name.equals("ignore"))
        .filterValues(number -> number % 2 == 0)
        .mapValues(number -> number / 2)
        .toMap();
```

instead of 

```java
Map<String, Integer> map; // = ["five" -> 5, "one" -> 1, "ten" -> 10, "ignore" -> - 10]
Map<String, Integer> dividedByTwo = 
    mapStream(map)
        .filter(entry -> {
            return !entry.getKey().equals("ignore") && entry.getValue() % 2 == 0;
        })
        .collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue() / 2
        ));
```
