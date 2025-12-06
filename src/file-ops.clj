(ns file-ops)

(defn read-instructions [f]
  (with-open [rdr (io/reader f)]
  (reduce conj [] (line-seq rdr))))
