(ns day4
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [clojure.set :as st])
  (:require [file-ops :as fops])
  (:require [string-ops :as sops])
  (:require [clojure.java.io :as io]))

; i need a function that takes a string, splits it on "-" into L and R
; then it converts the strings to Longs with edn to pass into a range
; finally it consumes that range to create a vec of vec of strings
; and deduped and flattened potentially via set operations to be returned
(defn range-extractor [acc r] (
  let [split-range (map edn/read-string (sops/split-dash r))] 
    (conj acc (range (first split-range) (+ (second split-range) 1)))))

(defn solve [f]
  (let [
        lines (vec (fops/read-instructions f))
        instruction-split (reduce (fn[acc i](cond
                                              (= "" (nth lines i)) i
                                              :else (cond
                                                (not (= acc 0)) acc
                                                :else 0)
                                            )) 0 (range (count lines)))
        checks (set (flatten (reduce range-extractor [] (subvec lines 0 instruction-split))))
        ingredients (vec (map edn/read-string (flatten (subvec lines (+ instruction-split 1)))))
        result (reduce (fn[[checks acc] i] 
                         [checks (cond
                                   (contains? checks i) (inc acc)
                                   :else acc
                                   )]) 
                       [checks 0] 
                       ingredients)
        ]
    (second result)
  ))

(println (solve "fixtures/day5.simple"))
(println (= 3 (solve "fixtures/day5.simple")))
(println (solve "fixtures/day5.full"))
