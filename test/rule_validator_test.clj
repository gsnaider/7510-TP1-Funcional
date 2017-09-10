(ns rule-validator-test
  (:require [clojure.test :refer :all]
            [rule-validator :as validator]
            [data-model])
  (:import [data_model Rule Fact]))

(deftest validate-rule-test
  (testing "valid-rule? returns true for valid rule strings."
    (is (validator/valid-rule? "hijo(X, Y) :- varon(X), padre(Y, X)."))
    (is (validator/valid-rule? "hija(X, Y) :- mujer(X), padre(Y, X)."))
    (is (validator/valid-rule? "subtract(X, Y, Z) :- add(Y, Z, X)."))
    (is (validator/valid-rule? "subtract(X, Y, Z) :- add(Y, Z, X)."))
    (is (validator/valid-rule? "wet_floor(X) :- rain(X).")))

  (testing "valid-rule? returns false for invalid rule strings."
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

(deftest validate-rule-params
  (testing "valid-rule-params? returns true for rules with the same parameters in 
    its name and in its facts."
    (is (validator/valid-rule-params? 
      (new Rule
        "hijo"
        '("X" "Y")
        #{(new Fact "varon" '("X")) (new Fact "padre" '("Y" "X"))})))
    
    (is (validator/valid-rule-params? 
      (new Rule "subtract" '("X" "Y" "Z") #{(new Fact "add" '("Y" "Z" "X"))})))
    
    (is (validator/valid-rule-params? 
      (new Rule "wet_floor" '("X") #{(new Fact "rain" '("X"))}))))

  (testing "valid-rule-params? returns false for rules with different parameters in 
    its name than in its facts."
    (is (not (validator/valid-rule-params? 
      (new Rule "hijo" '("X") #{(new Fact "varon" '("Y"))}))))

    (is (not (validator/valid-rule-params?
      (new Rule
        "hijo"
        '("X")
        #{(new Fact "varon" '("X")) (new Fact "padre" '("Y" "X"))}))))
    
    (is (not (validator/valid-rule-params?
      (new Rule "hijo" '("X" "Y") #{(new Fact "varon" '("Y"))}))))))