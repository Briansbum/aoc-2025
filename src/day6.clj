(ns day5
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [file-ops :as fops])
  (:require [string-ops :as sops])
  (:require [clojure.java.io :as io]))

(defn the-number-cruncher [[acc st line] i] 
  (cond 
    (s/blank? (str (nth line i))) 
      [(conj acc st) [] line]
    :else 
      [acc (conj st (nth line i)) line]))

(defn split-whitespace [line] (
   let [
    crunchos (reduce the-number-cruncher [[] [] line] (range (count line)))
    all-crunchos (conj (first crunchos) (second crunchos))
    final-crunchos (filterv (complement nil?) (map edn/read-string (map s/join all-crunchos)))
   ] 
   final-crunchos))

(defn do-sum [values] 
  (let [
        op (str (first values))
       ]
    (cond
      (= op "*") (apply * (rest values))
      (= op "+") (apply + (rest values))
      :else (println "error"))))

(defn pull-value-from-flat [[acc flat] value-index]
  [(conj acc (nth flat value-index)) flat])

(defn solve [f] 
  (let [
        lines (map split-whitespace (fops/read-instructions f))
        line-count (count lines)
        cols (count (nth lines 0))
        ; we need to flatten lines and then range over cols, then
        ; we need to range over line-count and accumulate the values at
        ; indices that match (prev-index + cols)
        ; once we have all of values we reduce over them with do-sum and the
        ; operation that's at (index + (cols * line-count)
        values (map reverse (first (reduce (fn [[acc flat line-count cols] column-index] 
                         (let [
                               values (reduce pull-value-from-flat [[] flat] 
                                              (range column-index (* line-count cols) cols))
                              ] 
                           [(conj acc (first values)) flat line-count cols])) 
                       [[] (flatten lines) line-count cols] 
                       (range cols))))
        result (reduce + (map do-sum values))
       ]
    result))

(println (solve "fixtures/day6.simple"))
(println (= 4277556 (solve "fixtures/day6.simple")))
(println (solve "fixtures/day6.full"))
