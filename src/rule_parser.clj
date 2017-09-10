(ns rule-parser
  (:require [data-model]
            [parser-util]
            [rule-validator]
            [clojure.string :as str])
  (:import [data_model Fact Rule]))

(def rule-assign-code ":-")
(def rule-facts-separator-regex #",(?![^\(]*\))")

(defn- new-fact
  "Returns a new Fact representing the string fact-line"
  [fact-line]
  (new Fact
    (parser-util/get-name fact-line)
    (parser-util/get-params fact-line)))

(defn- get-rule-facts
  "Returns a set containing all the facts from a rule-line."
  [rule-line]
  (set
    (map new-fact
      (-> rule-line
          (str/split (re-pattern rule-assign-code))
          (nth 1)
          (str/split (re-pattern rule-facts-separator-regex))))))

(defn- rule-line->rule
  "Converts an input string line to a Rule,
  or throws an Exception if the rule-line is invalid."
  [rule-line]
  (if-not (rule-validator/valid-rule? rule-line)
    (throw (IllegalArgumentException. "Invalid rule.")))
  (let [rule (new Rule
              (parser-util/get-name rule-line)
              (parser-util/get-params rule-line)
              (get-rule-facts rule-line))]
    (if (rule-validator/valid-rule-params? rule)
      rule
      (throw (IllegalArgumentException. "Invalid rule parameters.")))))

(defn get-rules
  "Returns a set containing all the rules from the database,
  or throws an Exception if the databse can't be parsed."
  [database]
  (->> (parser-util/get-lines database)
        (remove #(not (str/includes? % rule-assign-code)))
        (map rule-line->rule)
        (set)))