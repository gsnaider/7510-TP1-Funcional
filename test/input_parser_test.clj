(ns input-parser-test
  (:require [clojure.test :refer :all]
            [input-parser :as parser])
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

(deftest facts-test
  (testing "facts returns list of facts from database."
    (is (= (parser/get-facts incomplete-database) 
            (list 
              (new Fact "varon" (list "juan"))
              (new Fact "padre" (list "juan" "pepe"))))))

  (testing "facts throws Exception with incomplete database."
    (is (thrown? Exception (doall (parser/get-facts incomplete-database))))))