(ns invalid-database-test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))

(def invalid-database "
	varon(juan).
	hijo(X) :- varon(Y).
")

(deftest invalid-database-fact-test
  (testing "varon(juan) should be nil"
    (is (= (evaluate-query invalid-database "varon(juan)")
           nil))) 
  (testing "varon(maria) should be nil"
    (is (= (evaluate-query invalid-database "varon(maria)")
           nil))) 
  (testing "mujer(cecilia) should be nil"
    (is (= (evaluate-query invalid-database "mujer(cecilia)")
           nil))) 
  (testing "padre(juan, pepe) should be nil"
    (is (= (evaluate-query invalid-database "padre(juan, pepe)")
           nil))) 
  (testing "padre(mario, pepe) should be nil"
    (is (= (evaluate-query invalid-database "padre(mario, pepe)")
           nil))))

(deftest invalid-database-rule-test
  (testing "hijo(pepe, juan) should be nil"
    (is (= (evaluate-query invalid-database "hijo(pepe, juan)")
           nil))) 
  (testing "hija(maria, roberto) should be nil"
    (is (= (evaluate-query invalid-database "hija(maria, roberto)")
           nil))))
