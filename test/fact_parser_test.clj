(ns fact-parser-test
  (:require [clojure.test :refer :all]
            [fact-parser :as parser])
  (:import [data_model Fact]))

(def parent-database "
	varon(juan).
	padre(juan, pepe).
	hijo(X, Y) :- varon(X), padre(Y, X).
	hija(X, Y) :- mujer(X), padre(Y, X).
")

(def incomplete-database "
  varon(juan).
  varon
")

(deftest get-facts-test
  (testing "get-facts returns a list of facts from a valid database."
    (is (= (parser/get-facts parent-database)
            #{(new Fact "varon" (list "juan"))
              (new Fact "padre" (list "juan" "pepe"))})))

  (testing "get-facts throws Exception with an incomplete database."
    (is (thrown? Exception (doall (parser/get-facts incomplete-database))))))
