(ns day3
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [clojure.java.io :as io]))

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn find-largest-from-index [[haystack acc index end-buffer escape] needle]
    (let [first-pos (s/index-of haystack (str needle) index)]
      (cond
        (nil? first-pos) [haystack acc index end-buffer escape]
        (true? escape) [haystack acc index end-buffer escape]
        (= first-pos (- (count haystack) end-buffer)) [haystack acc index end-buffer escape]
        :else [haystack needle first-pos end-buffer true])))

(defn process-line [line]
  (let [loc1 (reduce find-largest-from-index [line 0 0 1 false] (range 9 0 -1))
      loc2 (reduce find-largest-from-index [line 0 (+ (nth loc1 2) 1) 0 false] (range 9 0 -1))]
      (edn/read-string (format "%s%s" (nth line (nth loc1 2)) (nth line (nth loc2 2))))
  ))

(println (->> (read-instructions "fixtures/day3.simple")
            (map process-line)
            (reduce +)
            ))
(println (= 357 (->> (read-instructions "fixtures/day3.simple")
            (map process-line)
            (reduce +)
            )))
(println (->> (read-instructions "fixtures/day3.full")
            (map process-line)
            (reduce +)
            ))
(println (= 16854 (->> (read-instructions "fixtures/day3.full")
            (map process-line)
            (reduce +)
            )))
