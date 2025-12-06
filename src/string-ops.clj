(ns string-ops
  (:require [clojure.string :as s]))

(defn split-on-space [search]
  (s/split search #"\s"))

(defn split-dash [id]
  (s/split id #"-" -1))

(defn split-comma [line]
  (s/split line #"," -1))
