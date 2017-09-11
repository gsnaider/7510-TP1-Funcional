(ns query-parser-test
  (:require [clojure.test :refer :all]
            [query-parser :as parser])
  (:import [data_model Query]))

(deftest parse-query-test
  (testing "parse-query returns a Query from a valid query string."
    (is (= (parser/parse-query "varon(juan)")
              (new Query "varon" (list "juan")))))

  (testing "parse-query returns a Query from a valid query string
            with multiple parameters."
    (is (= (parser/parse-query "padre(juan, pepe)")
              (new Query "padre" (list "juan" "pepe")))))

  (testing "parse-query throws an Exception with an invalid query string."
    (is (thrown? Exception (parser/parse-query "varon")))))