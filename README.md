# awaitility-clj

Clojure wrapper for the [Awaitility](http://www.awaitility.org/) Java library.

[![Clojars Project](https://img.shields.io/clojars/v/ai.mypulse/awaitility-clj.svg)](https://clojars.org/ai.mypulse/awaitility-clj)

## Examples

```clojure
(:require [awaitility-clj.core :refer [wait-for]])

(let [start-time (System/currentTimeMillis)
      end-time (+ start-time 200)]
  
  (wait-for {:at-most [1 :seconds]} ; options such as at-most and poll-interval
    (fn [] (>= (System/currentTimeMillis) end-time)))) ; function that will eventually return true
```

## License

Copyright © 2023 MyPulse Ltd.

Distributed under the MIT license.
