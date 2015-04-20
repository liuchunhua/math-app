(ns math-app.core-test
  (:require [clojure.test :refer :all]
            [math-app.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (not= 0 1))))
(deftest hanglieshi-test
  (testing "行列式求值"
    (let [v3 [[1 2 -4] [-2 2 1] [-3 4 -2]]]
      (is (= (hanglieshi v3) -14.0)))))
