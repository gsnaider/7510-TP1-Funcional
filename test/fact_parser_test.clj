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

(deftest parse-facts-test
  (testing "parse-facts returns a set of facts from a valid database."
    (is (= (parser/parse-facts parent-database)
            #{(new Fact "varon" (list "juan"))
              (new Fact "padre" (list "juan" "pepe"))})))

  (testing "parse-facts throws Exception with an incomplete database."
    (is (thrown? Exception (doall (parser/parse-facts incomplete-database))))))
