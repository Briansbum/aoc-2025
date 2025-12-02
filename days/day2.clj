(ns day2
  (:require [clojure.java.io :as io]))

(defn parse-line [line])

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
    (mapv parse-line (line-seq rdr))))

(defn solve [xs])

(solve (read-instructions "fixtures/day2.simple"))
