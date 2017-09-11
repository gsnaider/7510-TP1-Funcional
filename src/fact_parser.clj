(ns fact-parser
  (:require [data-model]
            [parser-util]
            [fact-validator]
            [clojure.string :as str])
  (:import [data_model Fact]))

(def rule-assign-code ":-")

(defn- parse-fact
  "Converts a fact-string to a Fact,
  or throws an Exception if the conversion is not possible."
  [fact-string]
  (if (fact-validator/valid-fact? fact-string)
    (new Fact
      (parser-util/parse-name fact-string) (parser-util/parse-params fact-string))
    (throw (IllegalArgumentException. "Invalid fact."))))

(defn parse-facts
  "Returns a set containing all the facts from the database,
  or throws an Exception if the databse can't be parsed."
  [database]
  (->> (parser-util/parse-lines database)
        (remove #(str/includes? % rule-assign-code))
        (map parse-fact)
        set))
