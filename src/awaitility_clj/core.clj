(ns awaitility-clj.core
  (:import
   (java.time
     Duration)
   (java.time.temporal
     ChronoUnit)
   (org.awaitility
     Awaitility)
   (org.awaitility.core
     ConditionFactory)
   [org.awaitility.pollinterval PollInterval]))

(defn- keyword->chrono-unit
  [keyword-unit]
  (case keyword-unit
    :seconds ChronoUnit/SECONDS
    :milliseconds ChronoUnit/MILLIS))

(defn- tuple->duration
  [[amount unit]]
  (Duration/of amount (keyword->chrono-unit unit)))

(defn- apply-option
  [^ConditionFactory factory [option-key option-data]]
  (case option-key
    :at-most (.atMost factory ^Duration (tuple->duration option-data))
    :at-least (.atLeast factory ^Duration (tuple->duration option-data))
    :poll-interval (if (vector? option-data)
                     (.pollInterval factory ^Duration (tuple->duration option-data))
                     (.pollInterval factory ^PollInterval option-data))))

(defn- apply-options
  [^ConditionFactory factory options]
  (reduce apply-option factory options))

(defn wait-for
  [options until-fn]
  (-> (Awaitility/await)
      (apply-options options)
      (.until until-fn)))
