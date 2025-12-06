(ns day5
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [file-ops :as fops])
  (:require [string-ops :as sops])
  (:require [clojure.java.io :as io]))

(defn range-extractor [acc r] (
  let [split-range (map edn/read-string (sops/split-dash r))] 
    (conj acc (vec (list (first split-range) (second split-range))))))

(defn check-reducer [[acc i] c] (cond
                                (and (<= (nth c 0) i) (<= i (nth c 1))) (reduced [(inc acc) i])
                                :else [acc i]))

(defn solve [f]
  (let [
        lines (vec (fops/read-instructions f))
        instruction-split (reduce (fn[acc i](cond
                                              (= "" (nth lines i)) i
                                              :else (cond
                                                (not (= acc 0)) acc
                                                :else 0)
                                            )) 0 (range (count lines)))
        checks (vec (reduce range-extractor [] (subvec lines 0 instruction-split)))
        ingredients (vec (map edn/read-string (flatten (subvec lines (+ instruction-split 1)))))
        result (reduce (
                fn[[checks acc] i] [
                                    checks 
                                    (first (reduce check-reducer [acc i] checks))
                                   ]) 
                [checks 0] ingredients)
        ]
    (second result)
  ))

(println (solve "fixtures/day5.simple"))
(println (= 3 (solve "fixtures/day5.simple")))
(println (solve "fixtures/day5.full"))
