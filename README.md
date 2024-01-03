# awaitility-clj

Clojure wrapper for the [Awaitility](http://www.awaitility.org/) Java library.

[![Clojars Project](https://img.shields.io/clojars/v/uk.co.jordanrobinson/awaitility-clj.svg)](https://clojars.org/uk.co.jordanrobinson/awaitility-clj)

## Examples

```clojure
(:require [awaitility-clj.core :refer [wait-for]])

(let [start-time (System/currentTimeMillis)
      end-time (+ start-time 200)]
  
  (wait-for {:at-most [1 :seconds]} ; options such as at-most and poll-interval
    (fn [] (>= (System/currentTimeMillis) end-time)))) ; function that will eventually return true
```

## License

Copyright © 2024 Jordan Robinson

Distributed under the MIT license.

Originally forked from the MyPulse [awaitility-clj](https://github.com/mypulse-uk/awaitility-clj) library, also MIT licensed.
