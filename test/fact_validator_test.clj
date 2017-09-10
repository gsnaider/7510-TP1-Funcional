(ns fact-validator-test
  (:require [clojure.test :refer :all]
            [fact-validator :as validator]))

(deftest validate-fact-test
  (testing "valid-fact? returns true for valid facts."
    (is (validator/valid-fact? "varon(juan)."))
    (is (validator/valid-fact? "padre(juan, pepe).")))

  (testing "valid-fact? returns false for invalid facts."
    (is (not (validator/valid-fact? "varon")))
    (is (not (validator/valid-fact? "varon.")))
    (is (not (validator/valid-fact? "varon().")))
    (is (not (validator/valid-fact? "varon(juan)")))
    (is (not (validator/valid-fact? "varon(juan, ).")))
    (is (not (validator/valid-fact? "varon(juan.")))
    (is (not (validator/valid-fact? "(juan).")))))