(ns day7
  (:require [clojure.edn :as edn])
  (:require [clojure.string :as s])
  (:require [file-ops :as fops])
  (:require [string-ops :as sops])
  (:require [clojure.java.io :as io])
  (:require [clojure.set :as cset]))

(defn fetch-indices [[acc line prev-index] index] 
  (let[
       i (= (nth line index) \^)
      ]
    (cond
      (false? i) [acc line index]
      :else [(conj acc index) line index])))

(defn one-more-one-less [acc i] (cset/union acc [(dec i) (inc i)]))

(defn check-line [pipe-locs line]
  (let [
        f (println "------------------new line----------------------")
        f (println "pipe-locs: " pipe-locs)
        f (println "line:      " line)
        ; caret-locs contains the indicies of each ^ in this line as a set
        caret-locs (set (first (reduce fetch-indices [[] line 0] (range (count line)))))
        f (println "caret-locs: " caret-locs)
        ; next each caret needs to generate two pipes into new-pipes-locs
        new-pipes-locs (set (reduce one-more-one-less pipe-locs caret-locs))
        f (println "new-pipes-locs:" new-pipes-locs)
        ; new-pipes-locs needs to be turned into a set

        ; pipe-locs (also a set) needs to difference() with caret-locs, call it pl-diff
        pl-diff (cset/difference pipe-locs caret-locs)
        npl-diff (cset/difference new-pipes-locs caret-locs)

        f (println "pl-diff: " pl-diff)
        ; then (union new-pipes-locs pl-diff) should be the return
        f (println "result-union: " (cset/union npl-diff pl-diff))
        
       ] (cset/union npl-diff pl-diff)))

(defn solve [f]
  (let [
        lines (fops/read-instructions f)

        ; get the first index to set state and start looping from (rest lines)
        s-index (s/index-of (first lines) \S)
        f (println (first lines) s-index)

        ; 
        result (count (reduce check-line (set [s-index]) (rest lines)))
       ] 
    result))

(println "result: " (solve "fixtures/day7.simple"))
;(println (= 21 (solve "fixtures/day7.simple")))
