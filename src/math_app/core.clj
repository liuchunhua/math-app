(ns math-app.core
  (:gen-class))
(use '[clojure.set])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn nixu
  "计算自然数排列中某个元素的逆序数(标准次序为从大到小)"
  [i coll]
  (count (filter #(> (coll %) (coll i)) (range i))))
(defn nixushu
  "计算自然数排列的逆序数"
  [coll]
  (reduce + (map #(nixu % coll) (range (count coll)))))

(defn nextAxis
  "行列式中对角线下一个坐标,example:
  (nextAxis 2 [0 0])
  => [1 1]"
  [n coll]
  (let [n n]
    (vec (map #(rem (+ % 1) n) coll))))

(defn fanNextAxis
  "行列式中反对角线下一个坐标,example:
  (fanNextAxis 3 [0 0])
  =>[1 2]"
  [n coll]
  (vector (inc (coll 0))  (rem (+ n (dec (coll 1))) n)))

(defn duijiaoxian
  "行列式对角线坐标向量
  三阶行列式对角线坐标
  (duijiaoxian 3 [0 0])
  =>[[0 0] [1 1] [2 2]]"
  [n coll]
  (loop [index [coll] i 0] (do (if (>= i (dec n)) index (recur (conj index (nextAxis n (last index))) (inc i))))))

(defn fanduijiaoxian
  "行列式反对角线坐标向量
  三阶行列式反对角线坐标
  (fanduijiaoxian 3 [0 0])
  =>[[0 0] [1 2] [2 1]]"
  [n coll]
  (loop [index [coll] i 0] (do (if (>= i (dec n)) index (recur (conj index (fanNextAxis n (last index))) (inc i))))))

(defn elementsMulti
  "行列式坐标向量的积"
  [coll axises]
  (reduce * (map #(get-in coll %) axises)))

(defn pailie
  "穷举列表的排列
  (pailie [\a \b \c])
  =>([\a \b \c] [\a \c \b] [\b \a \c] [\b \c \a] [\c \a \b] [\c \b \a])
  [\a] [\b] [\c]
  [\a \b] [\a \c] [\b \a] [\b \c] [\c \a] [\c \b]
  [\a \b \c] [\a \c \b] [\b \a \c] [\b \c \a] [\c \a \b] [\c \b \a]"
  [coll]
  (loop [m (for [ i coll] (vector i))
         n (set coll)]
    (if (empty? (difference n (first m)))
      m
      (recur
       (reduce concat (for [i m]
            (map #(conj i %) (difference n i)))) n)
      )))

(defn elements
  "n阶行列式n个元素的下标
  (elements (pailie [0 1]))
  =>(([0 0] [1 1]) ([0 1] [1 0]))"
  [coll]
  (for [m coll] (map #(vector %1 %2) (range (count m)) m)))

(defn hanglieshi
  "计算行列式的值
  (def v3 [[1 2 -4] [-2 2 1] [-3 4 -2]])
  (hanglieshi v3)
  =>-14.0"
  [coll]
  (let [
        p (pailie (range (count coll)))
        m (map #(elementsMulti coll %) (elements p))
        n (map nixushu p)]
    (reduce + (map #(* %1 %2) m (for [i n] (Math/pow -1 i))))
    )
  )
