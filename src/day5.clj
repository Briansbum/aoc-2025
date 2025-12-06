(ns day4
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [clojure.java.io :as io]))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

; i need a function that takes a string, splits it on "-" into L and R
; then it converts the strings to Longs with edn to pass into a range
; finally it consumes that range to create a vec of vec of strings
; and deduped and flattened potentially via set operations to be returned
(defn split-dash


(defn solve [f]
  (let [
        lines (vec (read-instructions f))
        instruction-split (s/index-of lines " ")
        ranges (subvec lines 0 instruction-split)
        ingredients (subvec lines (+ instruction-split 1))
        ] (

           )))

(println (solve "fixtures/day5.simple"))
