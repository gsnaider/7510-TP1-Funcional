(ns parser-util-test
  (:require [clojure.test :refer :all]
            [parser-util]))

(def parent-database "
  varon(juan).
  padre(juan, pepe).
  hijo(X, Y) :- varon(X), padre(Y, X).
")

(deftest get-name-test
  (testing "get-name returns name of input-expression."
    (is (= (parser-util/get-name "varon(juan).") 
      "varon"))
    (is (= (parser-util/get-name "padre(juan, pepe).") 
      "padre"))
    (is (= (parser-util/get-name "hijo(juan, pepe)") 
      "hijo"))
    (is (= (parser-util/get-name "hijo(X, Y) :- varon(X), padre(Y, X).") 
      "hijo"))))

(deftest get-params-test
  (testing "get-params returns the params of an input-expression."
    (is (= (parser-util/get-params "varon(juan).") 
      '("juan")))
    (is (= (parser-util/get-params "padre(juan, pepe).") 
      '("juan" "pepe")))
    (is (= (parser-util/get-params "hijo(juan, pepe)") 
      '("juan" "pepe")))
    (is (= (parser-util/get-params "hijo(X, Y) :- varon(X), padre(Y, X).") 
      '("X" "Y")))))

(deftest get-lines-test
  (testing "get-lines returns database as a list of trimmed lines")
    (is (= (parser-util/get-lines parent-database)
      '("varon(juan)."
        "padre(juan, pepe)."
        "hijo(X, Y) :- varon(X), padre(Y, X)."))))