(ns rule-parser-test
  (:require [clojure.test :refer :all]
            [rule-parser :as parser])
  (:import [data_model Rule Fact]))

(def parent-database "
	varon(juan).
	padre(juan, pepe).
	hijo(X, Y) :- varon(X), padre(Y, X).
	hija(X, Y) :- mujer(X), padre(Y, X).
")

(def incomplete-database "
  varon(juan).
  hijo(X, Y) :- varon
")

(deftest get-rules-test
  (testing "get-rules returns a set of rules from a valid database."
    (is (= (parser/get-rules parent-database)
            #{(new Rule 
                "hijo"
                (list "X" "Y")
                #{(new Fact "varon" (list "X"))
                  (new Fact "padre" (list "Y" "X"))})
              (new Rule
                "hija"
                (list "X" "Y")
                #{(new Fact "mujer" (list "X"))
                  (new Fact "padre" (list "Y" "X"))})})))

  (testing "get-rules throws Exception with an incomplete database."
    (is (thrown? Exception (doall (parser/get-rules incomplete-database))))))
