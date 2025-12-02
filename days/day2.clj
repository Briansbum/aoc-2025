(ns day2
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.edn :as edn])
  (:require [clojure.math :as math]))

; I'm given some int ranges in the form:
; 95-115,998-1012,1188511880-1188511890
; I need to sep the ranges on , and then for each int in the range
; I need to step through them and see if any int has a repeating pattern
; for example 95-115 contains 99 but not 111
; 111 repeats 3 times but 99 twice

(defn split-dash [id]
  (str/split id #"-" -1))

(defn split-comma [line]
  (str/split line #"," -1))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn check-id [id] 
  (let [id-str (str id)
        id-str-len (count id-str)]
    ; Try all possible pattern sizes from 1 to half the length
    (some (fn [pattern-size]
      (if (and (pos? pattern-size)
               (zero? (mod id-str-len pattern-size))
               (>= (/ id-str-len pattern-size) 2))
        (let [pattern (subs id-str 0 pattern-size)
              expected (apply str (repeat (/ id-str-len pattern-size) pattern))]
          (if (and (= id-str expected)
                   (not= (first pattern) \0))
            id
            nil))
        nil))
      (range 1 (+ 1 (quot id-str-len 2))))))

(defn count-ids-in-range [[left right]] 
  ; range's upper bound is exclusive but we want it to be inclusive
  (map check-id (range (edn/read-string left) (+ 1 (edn/read-string right)))))

(defn solve [f]
  (->> (split-comma (first (read-instructions f)))
       (map split-dash)
       (mapcat count-ids-in-range)
       (flatten)
       (filter some?)
       (reduce +)
  )
)

;(println (solve "fixtures/day2.simple"))
;(println (= 1227775554 (solve "fixtures/day2.simple")))

;(println (solve "fixtures/day2.full"))
;(println (= 52316131093 (solve "fixtures/day2.full")))

(println (= 4174379265 (solve "fixtures/day2.simple")))

