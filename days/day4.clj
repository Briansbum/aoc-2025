(ns day4
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [clojure.java.io :as io]))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

(comment
  It's the old 'you're looking at one line and need to check the others'
  what we need to do is to operate on the entire 2 collection at once
  we maintain a refernce to x and y and increment them at the same time
  okay actually we only need to get the line length and then we can flatten
  and move across the line in one direction with multiplication and division
)

(defn split-on-space [search]
  (s/split search #"\s"))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn search [[fs acc] index]
  (let [
    result (cond
            (< index 0) [fs acc]                  ; index is less than zero
            (> index (- (count fs) 1)) [fs acc]    ; index is greater than fs len - 1
            (= (nth fs index) \@) [fs (+ acc 1)] ; fs at index is @, 
            :else [fs acc])                         ; fs is probably at .
            ]
    (println (second result))
    result
  ))

(def neighbor-deltas [[-1 -1] [-1 0] [-1 1]
                      [0 -1]         [0 1]
                      [1 -1]  [1 0]  [1 1]])

(defn solve [[fs line-len acc] index]
  ; we now have the index so we need to just count the number 
  ; of cells with @'s in the surrounding 8 cells
  ; for each index we need to calculate first the grid location and then the 
  ; 8 surrounding cells. We can bin off any that are not in bounds
   (println line-len acc index (nth fs index))
  (if (= (nth fs index) \@)
  (let [positions [
                  (- index (+ line-len 1))
                  (- index line-len)
                  (- index (- line-len 1))
                  (+ index 1)
                  (+ index (+ line-len 1))
                  (+ index line-len)
                  (+ index (- line-len 1))
                  (- index 1)
                   ]]
    [fs line-len (+ acc 
                    (if (> 4 (
                              second (
                                      reduce search [fs 0] positions))
                              ) 1 0))])
    [fs line-len acc]))

(let [
      instructions (read-instructions "fixtures/day4.simple")
      line-len (count (first instructions))
      flatstructions (mapcat seq instructions)
      result (reduce solve [flatstructions line-len 0] (range (count flatstructions)))] 
  (println (nth result 2)))
