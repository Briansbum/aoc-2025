; ALEX, the problem is that you go through the thing as a flat list and
; when you find a ^ check index-linelength and count if there is a |
; that is the whole problem

; it would be stylin' to do it in one forward pass. We can read char by char
; and perform the following logic:
;
; is char "^" and (index - line-len) != | then (+ acc 1)


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
        prev-locs (last pipe-locs)
;        f (println "------------------new line----------------------")
;        f (println "prev-locs: " prev-locs)
;        f (println "line:      " line)
        ; caret-locs contains the indicies of each ^ in this line as a set
        caret-locs (set (first (reduce fetch-indices [[] line 0] (range (count line)))))
;        f (println "caret-locs: " caret-locs)
        ; next each caret needs to generate two pipes into new-pipes-locs
        new-pipes-locs (set (reduce one-more-one-less prev-locs caret-locs))
;        f (println "new-pipes-locs:" new-pipes-locs)
        ; new-pipes-locs needs to be turned into a set

        ; prev-locs (also a set) needs to difference() with caret-locs, call it pl-diff
        pl-diff (cset/difference prev-locs caret-locs)
        npl-diff (cset/difference new-pipes-locs caret-locs)

;        f (println "pl-diff: " pl-diff)
        ; then (union new-pipes-locs pl-diff) should be the return
;        f (println "result-union: " (cset/union npl-diff pl-diff))
        
       ] (conj pipe-locs (cset/union npl-diff pl-diff))))

(defn process-char [[acc line-len pipes-lines flat-line] index] 
  (let [
;        f (println "-------------------new char-----------------------")
        prev-line-pipes (nth pipes-lines (dec (quot index line-len)))
;        f (println prev-line-pipes)
;        f (println "acc: " acc )
;        f (println "line-len: " line-len )
;        f (println "index: " index )
;        f (println "char: " (= \^ (nth flat-line index)))
;        f (println "pipe-index: " (- index line-len) )
;        f (println "pipe: " (nth flat-line (- index line-len)))
;        f (println "true-pipe-index: " (- index line-len))
;        f (println "curr-line-index: " (dec (quot index line-len)))
        pipe-index (set [(- (- index line-len) (* line-len (dec (quot index line-len))))])
        valid-pipe (cset/intersection pipe-index prev-line-pipes)
        ] 
  (cond
    (and 
      (= \^ (nth flat-line index))
      (> (count valid-pipe) 0))
     [(inc acc) line-len pipes-lines flat-line]
    :else 
     [acc line-len pipes-lines flat-line]
    )))

(defn solve [f]
  (let [
        lines (fops/read-instructions f)

        line-len (count (first lines))

        flat-line (s/join lines)

        s-index (s/index-of (first lines) \S)

        pipes-lines (reduce check-line [(set [s-index])] (rest lines))

        start-offset (dec (* line-len 2))

        result (first (reduce process-char [0 line-len pipes-lines flat-line] (range start-offset (count flat-line))))
       ] 
    result))

(println "result: " (solve "fixtures/day7.simple"))
(println (= 21 (solve "fixtures/day7.simple")))

