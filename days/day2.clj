(ns day2
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str]))

; I'm given some int ranges in the form:
; 95-115,998-1012,1188511880-1188511890
; I need to sep the ranges on , and then for each int in the range
; I need to step through them and see if any int has a repeating pattern
; for example 95-115 contains 99 but not 111
; 111 repeats 3 times but 99 twice

(defn split-dash [id]
  (println id)
  (str/split id #"-" -1))

(defn split-comma [line]
  (str/split line #"," -1))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn check-consecutive [acc id] 
  
  )

(defn partitioner [[id-str acc] window-size] 
  (partition window-size id-str))

(defn check-id [id] 
  ; we need to turn the id into a string so that seq works on it
  ; then we need windows on the string, we then search in the string
  ; for each window. This could actually be a good place for fold because
  ; we are going to brute force many options and collection is just +
  (let [
    id-str (str id)
    id-str-len (alength (to-array id-str))
    max-window-len (quot id-str-len 5)
    ; i over max-window-len and make partitions of id-str for each value of i
    windows (reduce partitioner [id-str []] (range max-window-len))]
    ; use the length as a range to compare each length of window against the rest of the int
    ; i feel in my bones that this is a bad idea but here we go
    ; at the very least there must be a clever option here where I can ignore any windows
    ; that are longer than half the total length. That could even have a noticeable runtime
    ; difference depending on the size of the data structure (????????) 

    
    (println windows)))

(defn count-ids-in-range [[left right]] 
  (map check-id (range (Integer/parseInt left) (Integer/parseInt right))))

(defn solve [f]
  (->> (split-comma (first (read-instructions "fixtures/day2.simple")))
       (map split-dash)
       (map count-ids-in-range)
  )
)

(println (solve "fixtures/day2.simple"))
