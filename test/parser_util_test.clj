(ns parser-util-test
  (:require [clojure.test :refer :all]
            [parser-util]))

(def parent-database "
  varon(juan).
  padre(juan, pepe).
  hijo(X, Y) :- varon(X), padre(Y, X).
")

(deftest parse-name-test
  (testing "parse-name returns name of input-expression."
    (is (= (parser-util/parse-name "varon(juan).") 
      "varon"))
    (is (= (parser-util/parse-name "padre(juan, pepe).") 
      "padre"))
    (is (= (parser-util/parse-name "hijo(juan, pepe)") 
      "hijo"))
    (is (= (parser-util/parse-name "hijo(X, Y) :- varon(X), padre(Y, X).") 
      "hijo"))))

(deftest parse-params-test
  (testing "parse-params returns the params of an input-expression."
    (is (= (parser-util/parse-params "varon(juan).") 
      '("juan")))
    (is (= (parser-util/parse-params "padre(juan, pepe).") 
      '("juan" "pepe")))
    (is (= (parser-util/parse-params "hijo(juan, pepe)") 
      '("juan" "pepe")))
    (is (= (parser-util/parse-params "hijo(X, Y) :- varon(X), padre(Y, X).") 
      '("X" "Y")))))

(deftest parse-lines-test
  (testing "parse-lines returns database as a list of trimmed lines")
    (is (= (parser-util/parse-lines parent-database)
      '("varon(juan)."
        "padre(juan, pepe)."
        "hijo(X, Y) :- varon(X), padre(Y, X)."))))