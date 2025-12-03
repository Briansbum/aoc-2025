(ns day3
  (:require [clojure.edn :as edn])
  (:require [clojure.java.io :as io]))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn process-line [line]
  (let [] 
      (reduce 
        (fn [acc i] (
          let [
            int-i (Character/digit i 10)]
          (if (> int-i acc) int-i acc)
          ))
        0 line)
    ))

(println (->> (read-instructions "fixtures/day3.simple")
            (map process-line)
            ))
