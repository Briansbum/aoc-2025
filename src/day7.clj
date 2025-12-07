(ns day7
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [file-ops :as fops])
  (:require [string-ops :as sops])
  (:require [clojure.java.io :as io]))

(comment
        prev-update-positions current-update-positions acc := [] [] 0
        for i, l := range lines
-          if i == 0; 
-            prev-update-postitions (index-of l "S")
-            continue

          for j, c := range l
            if (contains prev-update-positions j)
              if (= c \^)
                current-update-positions = append (dec j) (inc j)
                l[(dec j)] l[(inc j)] = "|" "|"
                (+ acc 2)
              else
                current-update-positions = append j
                l[j] = "|"

          previous-update-positions = (dedupe current-update-positions)
)

(defn position-printer [positions line]
  (let [
;        f (println positions line)
        cs (for [l1 line l2 (range (count line)) ])
;        f (println cs)
       ]()))

(defn character-checker [[acc curr-update-positions prev-update-positions line] i]
  (let [
        is-i-in-prev-update-positions ((complement nil?) (some #(= i %) prev-update-positions))
;        f (println prev-update-positions i is-i-in-prev-update-positions)

;        f(println curr-update-positions (dec i) i (inc i) (nth line i))
        is-char-splitter (= \^ (nth line i))
       ] (cond
           (and is-i-in-prev-update-positions is-char-splitter)
             [(+ acc 2) 
              (conj curr-update-positions (dec i) (inc i))
              prev-update-positions 
              line]
;           is-i-in-prev-update-positions
;             [acc (conj curr-update-positions i) prev-update-positions line]
           :else [acc curr-update-positions prev-update-positions line])))

(defn the-counter [[acc curr-update-positions prev-update-positions lines] line-index]
  (let [
        line (nth lines line-index)
        character-check-results (reduce character-checker [0 curr-update-positions prev-update-positions line] (range (count line)))
        curr-update-positions (set (second character-check-results))
        f (println (count curr-update-positions) line-index acc  (second character-check-results))
       ] 
       [(+ acc (count curr-update-positions)) [] curr-update-positions lines]))

(defn solve [f] 
  (let [
        lines (fops/read-instructions f)

        ; this trickery is because escaping early from a reduce is annoying
        ; luckily we have all of the input data so we know that line 1 is
        ; always just 'S' at some index and we can use that to set initial
        ; state while making the overall job easier
        s-index (s/index-of (first lines) \S)

        ; after dithcing the first line we can also skip every other line
        ; it doesn't do anything, at least for part 1
        line-range (range 2 (- (count lines) 1) 2)
        
        counter (reduce the-counter [0 [] (set (list s-index)) lines] line-range)
        
       ]
    counter))

(println (solve "fixtures/day7.simple"))
(println (= 21 (solve "fixtures/day7.simple")))
