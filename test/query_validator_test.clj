(ns query-validator-test
  (:require [clojure.test :refer :all]
            [query-validator :as validator]))

(deftest validate-query-test
  (testing "valid-query? returns true for valid queries."
    (is (validator/valid-query? "varon(juan)"))
    (is (validator/valid-query? "padre(juan, pepe)")))

  (testing "valid-query? returns false for invalid queries."
    (is (not (validator/valid-query? "varon")))
    (is (not (validator/valid-query? "varon")))
    (is (not (validator/valid-query? "varon()")))
    (is (not (validator/valid-query? "varon(juan).")))
    (is (not (validator/valid-query? "varon(juan, )")))
    (is (not (validator/valid-query? "varon(juan")))
    (is (not (validator/valid-query? "(juan)")))))