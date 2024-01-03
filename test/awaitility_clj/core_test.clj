(ns awaitility-clj.core-test
  (:require
    [awaitility-clj.core :refer [wait-for]]
    [clojure.test :refer [deftest is testing]])
  (:import
    (org.awaitility.core
      ConditionTimeoutException)))

(deftest wait-for-waits-until-condition-is-true
  (testing "wait-for waits until condition is true"
    (let [start-time (System/currentTimeMillis)
          end-time (+ start-time 200)]
      (wait-for {:at-most [1 :seconds]}
                (fn [] (>= (System/currentTimeMillis) end-time)))
      (is (>= (System/currentTimeMillis) end-time)))))

(deftest wait-for-throws-exception-if-condition-times-out
  (testing "wait-for throws ConditionTimeoutException if condition times out"
    (let [start-time (System/currentTimeMillis)
          end-time (+ start-time 1500)]
      (is (thrown?
            ConditionTimeoutException
            (wait-for
              {:at-most [200 :milliseconds]}
              (fn [] (>= (System/currentTimeMillis) end-time))))))))

(deftest wait-for-poll-intervals
  (testing "wait-for uses default poll interval if none set"
    (let [start-time (System/currentTimeMillis)
          end-time (+ start-time 300)
          calls (atom 0)]
      (wait-for {:at-most [1 :seconds]}
                (fn []
                  (swap! calls inc)
                  (>= (System/currentTimeMillis) end-time)))
      (is (= 3 @calls))))
  (testing "wait-for uses poll interval if set"
    (let [start-time (System/currentTimeMillis)
          end-time (+ start-time 190)
          calls (atom 0)]
      (wait-for {:at-most [1 :seconds]
                 :poll-interval [50 :milliseconds]}
                (fn []
                  (swap! calls inc)
                  (>= (System/currentTimeMillis) end-time)))
      (is (= 4 @calls)))))
