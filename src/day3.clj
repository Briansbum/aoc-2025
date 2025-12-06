(ns day3
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [file-ops :as fops])
  (:require [clojure.java.io :as io]))

(defn find-largest-from-index [[haystack acc index end-buffer escape] needle]
    (let [first-pos (s/index-of haystack (str needle) index)]
      (cond
        (nil? first-pos) [haystack acc index end-buffer escape]
        (true? escape) [haystack acc index end-buffer escape]
        (= first-pos (- (count haystack) end-buffer)) [haystack acc index end-buffer escape]
        :else [haystack needle first-pos end-buffer true])))

(defn locator [line acc index]
    (reduce find-largest-from-index [line 0 index 0 false] (range 9 0 -1)))

(defn process-line-2 [line]
  (let [limit 12
        first-location (reduce find-largest-from-index [line 0 0 1 false] (range 9 0 -1))]
    (reduce (fn [[line loc acc] limit] 
      (let [new-loc (locator line 0 (+ (nth loc 2) 1))] 
          [line new-loc (conj acc (nth new-loc 2))]))
      [line first-location [(nth first-location 2)]] (range limit))
  ))

(println (->> (fops/read-instructions "fixtures/day3.simple")
            (map process-line-2)
            (map println)
            ))
;(println (= 357 (->> (read-instructions "fixtures/day3.simple")
;            (map process-line)
;            (reduce +)
;            )))
;(println (->> (read-instructions "fixtures/day3.full")
;            (map process-line)
;            (reduce +)
;            ))
;(println (= 16854 (->> (read-instructions "fixtures/day3.full")
;            (map process-line)
;            (reduce +)
;            )))
