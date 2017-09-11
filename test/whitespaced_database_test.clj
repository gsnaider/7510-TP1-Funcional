(ns whitespaced-database-test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))

(def whitespaced-database "
  varon(  juan  ).
  varon(\t  pepe\t).
  varon(hector).
  varon(roberto).
  varon   (alejandro)   .
  mujer(maria).
  mujer   (cecilia)  .
  padre(   juan   , \t   pepe).
  padre(juan,pepa).
  padre(hector, \t\tmaria).
  padre(   roberto,      alejandro).
  padre(roberto, cecilia).
  hijo  (X  , Y)   :-  varon(  X)\t , padre\t  (Y  ,   X).
  hija(X,Y):-mujer(X),padre(Y,X).
")

(deftest whitespaced-database-fact-test
  (testing "varon(juan) should be true"
    (is (= (evaluate-query whitespaced-database "varon(   juan  )")
           true)))
  (testing "varon(maria) should be false"
    (is (= (evaluate-query whitespaced-database "varon(maria    )")
           false)))
  (testing "mujer(cecilia) should be true"
    (is (= (evaluate-query whitespaced-database "mujer  (  cecilia\t\t)")
           true)))
  (testing "padre(juan, pepe) should be true"
    (is (= (evaluate-query whitespaced-database "padre(juan,pepe)")
           true)))
  (testing "padre(mario, pepe) should be false"
    (is (= (evaluate-query whitespaced-database "padre(\t\tmario  \t ,  pepe)")
           false))))

(deftest whitespaced-database-rule-test
  (testing "hijo(pepe, juan) should be true"
    (is (= (evaluate-query whitespaced-database "hijo\t(   pepe,    juan)")
           true)))
  (testing "hija(maria, roberto) should be false"
    (is (= (evaluate-query whitespaced-database "hija(maria,roberto)")
           false))))

(deftest whitespaced-database-empty-query-test
  (testing "varon should be nil"
    (is (= (evaluate-query whitespaced-database "varon")
           nil)))
  (testing "maria should be nil"
    (is (= (evaluate-query whitespaced-database "maria")
           nil)))
  (testing "empty should be nil"
    (is (= (evaluate-query whitespaced-database "")
           nil))))
