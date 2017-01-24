(ns math-app.math
  (:gen-class))

;;codes from <<Programming Clojure>> Stuart Halloway, Aaron Bedra

(defn recur-fibo [n]
  ""
  (letfn [(fib
            [current next n]
            (if (zero? n)
              current
              (recur next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(defn lazy-seq-fibo
  ([]
   (concat [0 1] (lazy-seq-fibo 0N 1N)))
  ([a b]
   (let [n (+ a b)]
     (lazy-seq
      (cons n (lazy-seq-fibo b n))))))

(defn fibo []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0N 1N])))

(defn sqrt-iter
  "scip 牛顿法求平方根"
  [^double guess ^double x]
  (let [good-enough? (fn [g] (< (Math/abs (- (Math/pow g 2) x)) 0.001))
        improve (fn [g] (average g (/ x g)))]
    (loop [g guess]
      (if (good-enough? g)
        g
        (recur (improve g))))))

(defn sqrt
  [^double x]
  (sqrt-iter 1.0 x))

(defn cube-root-iter
  [^double guess x]
  (let [good-enough? (fn [g] (< (Math/abs (- (Math/pow g 3) x)) 0.001))
        improve (fn [y] (/ (+ (/ x (Math/pow y 2)) (* 2 y)) 3))]
    (loop [g guess]
      (if (good-enough? g)
        g
        (recur (improve g))))))

(defn cube-root
  "牛顿法立方根"
  [^double x]
  (cube-root-iter 1.0 x))
