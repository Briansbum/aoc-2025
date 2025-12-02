(ns day2
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.edn :as edn]))

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
  ; we need to turn the id into a string so that seq works on it
  ; then we need windows on the string, we then search in the string
  ; for each window. This could actually be a good place for fold because
  ; we are going to brute force many options and collection is just +
  (let [
    id-str (str id)
    id-str-len (alength (to-array id-str))
    parts (partition (quot id-str-len 2) id-str)]
    (if (= (mod id-str-len 2) 0)
      (if (not (= (first (first parts)) 0))
        (if (= (first parts) (second parts)) 
          id)))))

(defn count-ids-in-range [[left right]] 
  ; range's upper bound is exclusive but we want it to be inclusive
  (map check-id (range (edn/read-string left) (+ 1 (edn/read-string right)))))

(defn solve [f]
  (->> (split-comma (first (read-instructions f)))
       (map split-dash)
       (map count-ids-in-range)
       (flatten)
       (filter some?)
       (reduce +)
  )
)

(println (solve "fixtures/day2.simple"))
(println (= 1227775554 (solve "fixtures/day2.simple")))

(println (solve "fixtures/day2.full"))
