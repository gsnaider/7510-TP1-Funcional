(ns rule-validator-test
  (:require [clojure.test :refer :all]
            [rule-validator :as validator]))

(deftest validate-rule-test
  (testing "valid-rule? returns true for valid rules."
    (is (validator/valid-rule? "hijo(X, Y) :- varon(X), padre(Y, X)."))
    (is (validator/valid-rule? "hija(X, Y) :- mujer(X), padre(Y, X)."))
    (is (validator/valid-rule? "subtract(X, Y, Z) :- add(Y, Z, X)."))
    (is (validator/valid-rule? "subtract(X, Y, Z) :- add(Y, Z, X)."))
    (is (validator/valid-rule? "wet_floor(X) :- rain(X).")))

  (testing "valid-rule? returns false for invalid rules."
    (is (not (validator/valid-rule? "hijo(X, Y).")))
    (is (not (validator/valid-rule? "hijo(X, Y) :- .")))
    (is (not (validator/valid-rule? "hijo(X, Y) :- varon.")))
    (is (not (validator/valid-rule? "hijo(X, Y) :- varon().")))
    (is (not (validator/valid-rule? "hijo(X, Y) :- varon(X), .")))
    (is (not (validator/valid-rule? "hijo(X, Y) :- varon(X), padre.")))
    (is (not (validator/valid-rule? "hijo(X, Y) :- varon(X), padre().")))
    (is (not (validator/valid-rule? "hijo(X, Y) :- varon(X), padre(X, ).")))
    (is (not (validator/valid-rule? "hijo(X, Y) :- varon(X), padre(X, Y)")))
    (is (not (validator/valid-rule? "hijo :- varon(X), padre(X, Y).")))
    (is (not (validator/valid-rule? "hijo() :- varon(X), padre(X, Y).")))
    (is (not (validator/valid-rule? "hijo(X) varon(X), padre(X, Y).")))))