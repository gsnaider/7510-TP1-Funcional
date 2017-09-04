(ns query-parser-test
  (:require [clojure.test :refer :all]
            [query-parser :as parser])
  (:import [data_model Query]))

(deftest get-query-test
  (testing "get-query returns a Query from a valid query string."
    (is (= (parser/get-query "varon(juan)")
              (new Query "varon" (list "juan")))))

  (testing "get-query returns a Query from a valid query string
            with multiple parameters."
    (is (= (parser/get-query "padre(juan, pepe)")
              (new Query "padre" (list "juan" "pepe")))))

  (testing "get-query throws an Exception with an invalid query string."
    (is (thrown? Exception (parser/get-query "varon")))))