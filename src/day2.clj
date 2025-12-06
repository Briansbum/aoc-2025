(ns day2
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.edn :as edn])
  (:require [file-ops :as fops]
  (:require [string-ops :as sops]
  (:require [clojure.math :as math]))

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
  (->> (sops/split-comma (first (fops/read-instructions f)))
       (map sops/split-dash)
       (mapcat count-ids-in-range)
       (flatten)
       (reduce +)
  )
)

;(println (solve "fixtures/day2.simple"))
;(println (= 1227775554 (solve "fixtures/day2.simple")))

(println (solve "fixtures/day2.full"))
;(println (= 52316131093 (solve "fixtures/day2.full")))

(println (= 4174379265 (solve "fixtures/day2.simple")))

