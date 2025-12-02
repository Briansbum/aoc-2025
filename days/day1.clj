; The problem involves an array with values 0 through 99.
; There is a pointer that is able to jump from 0 to 99.
; The problem involves parsing a list of jump instructions and determining
; how many times the pointer jumps to 0.
; The starting index is 50

; The answer to fixtures/day1.simple is 3

(ns day1)

(def acc 0)
(def initial-arrow 50)
(def options 100)

(defn split-instruction [ins]
  (list (subs ins 0 1) (subs ins 1)))

(defn instructions [f]
  (with-open [rdr (clojure.java.io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn instructions-splits [xs]
  (map split-instruction xs))

(defn move-arrow [arrow count-loops direction jump]
  (def j (Integer/parseInt jump))

  ; this is the number of times the thing loops
  ; the number to jump is jump - ((jump / options) * options)
  (def loops (quot j options))
  (def i (if (> j options) 
    (- j (* loops options)) 
    j))
  (def clicks-from-loops (if (true? count-loops) loops 0))

  ; do not count a click if we are starting on 0
  (def started-at-zero (= arrow 0))

  (if (= "L" direction) 
  (if (> i arrow) 
    ; underflow - only count wrap if count-loops AND not starting at 0
    (let [new-pos (- options (- i arrow))]
      (list new-pos (+ clicks-from-loops (if (and count-loops (not started-at-zero)) 1 0))))
    ; move left
    (list (- arrow i) clicks-from-loops)) 
  (if (> i (- (- options 1) arrow))
    ; overflow - only count wrap if count-loops AND we don't land on 0
    (let [new-pos (- (- i (- (- options 1) arrow)) 1)]
      (list new-pos (+ clicks-from-loops (if (and count-loops (not= new-pos 0)) 1 0))))
    ; move right
    (list (+ arrow i) clicks-from-loops)))
)

(defn move-and-read [info ins]
  ; arrow-pos, loop-count-state, direction, jump
  (def move-arrow-res (move-arrow (first info) (nth info 2) (first ins) (second ins)))
  (if (= (first move-arrow-res) 0)
    ; new-arrow-pos, acc, loop-count-state
    (list (first move-arrow-res) (+ (second info) (second move-arrow-res) 1) (nth info 2))
    (list (first move-arrow-res) (+ (second info) (second move-arrow-res)) (nth info 2))
  )
)

(def out1 (reduce move-and-read (list initial-arrow 0 false) (instructions-splits (instructions "fixtures/day1-p1.simple"))))

(println ["Day 1 Part 1 Simple" (second out1)])

(def out2 (reduce move-and-read (list initial-arrow 0 false) (instructions-splits (instructions "fixtures/day1-p1.full"))))

(println ["Day 1 Part 1 Full" (second out2)])

(def out3 (reduce move-and-read (list initial-arrow 0 true) (instructions-splits (instructions "fixtures/day1-p1.simple"))))

(println ["Day 1 Part 2 Simple" (second out3)])

(def out4 (reduce move-and-read (list initial-arrow 0 true) (instructions-splits (instructions "fixtures/day1-p1.full"))))

(println ["Day 1 Part 2 Full" (second out4)])
