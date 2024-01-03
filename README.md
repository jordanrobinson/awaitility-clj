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

### `at-least` parameter

```clojure
;; Example of at-least parameter
(let [start-time (System/currentTimeMillis)
      end-time (+ start-time 200)] 

  (wait-for {:at-least [2 :seconds]} ; in this case we don't wait long enough
    (fn [] (>= (System/currentTimeMillis) end-time)))) 

; this returns -> org.awaitility.core.ConditionTimeoutException: Condition was evaluated in 200
; milliseconds which is earlier than expected minimum timeout 500 milliseconds
```
### Poll intervals
```clojure
;; can do either:
(let [start-time (System/currentTimeMillis)
      end-time (+ start-time 200)]

  (wait-for {:poll-interval [50 :milliseconds]} ; 50, 100, 150... milliseconds
    (fn [] (>= (System/currentTimeMillis) end-time))))

;; or
(:import [org.awaitility.pollinterval FibonacciPollInterval])

(let [start-time (System/currentTimeMillis)
      end-time (+ start-time 200)]

  (wait-for {:poll-interval (FibonacciPollInterval.)} ; 1, 1, 2, 3, 5, 8... milliseconds
    (fn [] (>= (System/currentTimeMillis) end-time))))
```

## License

Copyright © 2024 Jordan Robinson

Distributed under the MIT license.

Originally forked from the MyPulse [awaitility-clj](https://github.com/mypulse-uk/awaitility-clj) library, also MIT licensed.
