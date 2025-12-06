(ns day4
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [clojure.java.io :as io]))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn split-on-space [search]
  (s/split search #"\s"))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn search [[fs line-len acc] index]
  (let [
    result (cond
            (< index 0) [fs line-len acc]                 ; index is less than zero
            (> index (- (count fs) 1)) [fs line-len acc]  ; index is greater than fs len - 1
            (= (nth fs index) \@) [fs line-len (+ acc 1)] ; fs at index is @, 
            :else [fs line-len acc])                      ; fs is probably at .
            ]
    result
  ))

(defn solve [[fs line-len acc] index]
  ; we now have the index so we need to just count the number 
  ; of cells with @'s in the surrounding 8 cells
  ; for each index we need to calculate first the grid location and then the 
  ; 8 surrounding cells. We can bin off any that are not in bounds
  ; (println line-len acc index (nth fs index))
  (if (= (nth fs index) \@)
    (let [
          positions [
                    (- index (+ line-len 1)) ; top left
                    (- index line-len)       ; top
                    (- index (- line-len 1)) ; top right
                    (+ index 1)              ; right
                    (+ index (+ line-len 1)) ; bottom right
                    (+ index line-len)       ; bottom
                    (+ index (- line-len 1)) ; bottom left
                    (- index 1)              ; left
                     ]
          col (mod index line-len)
          filtered-positions (cond
                                 (= col 0) [
                                            (nth positions 1)
                                            (nth positions 2)
                                            (nth positions 3)
                                            (nth positions 4)
                                            (nth positions 5)] ; no left positions
                                 (= col (dec line-len)) [
                                            (nth positions 0)
                                            (nth positions 1)
                                            (nth positions 5)
                                            (nth positions 6)
                                            (nth positions 7)] ; no right positions
                                 :else positions ; we're in the middle of the grid
                                )]
      [fs line-len (+ acc 
                      (let [
                            result (reduce search [fs line-len 0] filtered-positions)
                           ]
                              (cond
                                (> 4 (nth result 2)) 1
                                :else 0)))])
      [fs line-len acc]))

(defn day4-part1 [f]
  (let [
        instructions (read-instructions f)
        line-len (count (first instructions))
        flatstructions (vec (mapcat seq instructions))
        result (reduce solve [flatstructions line-len 0] (range (count flatstructions)))] 
    (nth result 2)))

(println (= 13 (day4-part1 "fixtures/day4.simple")))
(println (day4-part1 "fixtures/day4.full"))
