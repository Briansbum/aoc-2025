; The problem involves an array with values 0 through 99.
; There is a pointer that is able to jump from 0 to 99.
; The problem involves parsing a list of jump instructions and determining
; how many times the pointer jumps to 0.
; The starting index is 50

; The answer to fixtures/day1.simple is 3

(ns day1)

(def acc 0)
(def initial_arrow 50)
(def options 100)

(defn split_instruction [ins]
  (list (subs ins 0 1) (subs ins 1)))

(defn instructions [f]
  (with-open [rdr (clojure.java.io/reader f)]
  (reduce conj [] (line-seq rdr))))

(defn instructions_splits [xs]
  (map split_instruction xs))

(defn move_arrow [arrow count_loops direction jump]
  (def j (Integer/parseInt jump))

  ; this is the number of times the thing loops
  ; the number to jump is jump - ((jump / options) * options)
  (def loops (quot j options))
  (def i (if (> j options) 
    (- j (* loops options)) 
    j))
  (def clicks_from_loops (if (true? count_loops) loops 0))

  ; do not count a click if we are starting on 0
  (def started_at_zero (= arrow 0))

  (if (= "L" direction) 
  (if (> i arrow) 
    ; underflow - only count wrap if count_loops AND not starting at 0
    (let [new_pos (- options (- i arrow))]
      (list new_pos (+ clicks_from_loops (if (and count_loops (not started_at_zero)) 1 0))))
    ; move left
    (list (- arrow i) clicks_from_loops)) 
  (if (> i (- (- options 1) arrow))
    ; overflow - only count wrap if count_loops AND we don't land on 0
    (let [new_pos (- (- i (- (- options 1) arrow)) 1)]
      (list new_pos (+ clicks_from_loops (if (and count_loops (not= new_pos 0)) 1 0))))
    ; move right
    (list (+ arrow i) clicks_from_loops)))
)

(defn move_and_read [info ins]
  ; arrow_pos, loop_count_state, direction, jump
  (def move_arrow_res (move_arrow (first info) (nth info 2) (first ins) (second ins)))
  (if (= (first move_arrow_res) 0)
    ; new_arrow_pos, acc, loop_count_state
    (list (first move_arrow_res) (+ (second info) (second move_arrow_res) 1) (nth info 2))
    (list (first move_arrow_res) (+ (second info) (second move_arrow_res)) (nth info 2))
  )
)

(def out1 (reduce move_and_read (list initial_arrow 0 false) (instructions_splits (instructions "fixtures/day1-p1.simple"))))

(println ["Day 1 Part 1 Simple" (second out1)])

(def out2 (reduce move_and_read (list initial_arrow 0 false) (instructions_splits (instructions "fixtures/day1-p1.full"))))

(println ["Day 1 Part 1 Full" (second out2)])

(def out3 (reduce move_and_read (list initial_arrow 0 true) (instructions_splits (instructions "fixtures/day1-p1.simple"))))

(println ["Day 1 Part 2 Simple" (second out3)])

(def out4 (reduce move_and_read (list initial_arrow 0 true) (instructions_splits (instructions "fixtures/day1-p1.full"))))

(println ["Day 1 Part 2 Full" (second out4)])
