(ns rule-parser
  (:require [data-model]
            [parser-util]
            [clojure.string :as str])
  (:import [data_model Fact Rule]))

(def rule-assign-code ":-")
(def rule-regex #"^\w+\((\w+)(, \w+)*\) :- (\w+\((\w+)(, \w+)*\))(, \w+\((\w+)(, \w+)*\))*\.$")

(defn- valid-rule?
  "Returns true if the string rule-line is a valid rule, or false otherwise."
  [rule-line]
  (not (nil? (re-matches rule-regex rule-line))))

(defn- rule-line->rule
  "Converts an input string line to a Rule,
  or throws an Exception if the conversion is not possible."
  [rule-line]
  (if (valid-rule? rule-line)
    (new Rule
      (parser-util/get-name rule-line)
      (parser-util/get-params rule-line)
      '())
    (throw (IllegalArgumentException. "Invalid rule."))))

(defn get-rules
  "Returns a set containing all the rules from the database,
  or throws an Exception if the databse can't be parsed."
  [database]
  (->> (parser-util/get-lines database)
        (remove #(not (str/includes? % rule-assign-code)))
        (map #(rule-line->rule %))
        (set)))