(ns string-ops)

(defn split-on-space [search]
  (s/split search #"\s"))

(defn split-dash [id]
  (str/split id #"-" -1))

(defn split-comma [line]
  (str/split line #"," -1))
