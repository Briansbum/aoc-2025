(ns day4
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [clojure.java.io :as io]))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

;(defn solve)

(defn day5-part1 [f]
  (let [
        lines (vec (read-instructions f))
        instruction-split (s/index-of lines " ")
        ranges (subvec lines 0 instruction-split)
        ingredients (subvec lines (+ instruction-split 1))
        ] (
           )))

(println (day5-part1 "fixtures/day5.simple"))
