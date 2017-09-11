(ns rule-parser
  (:require [data-model]
            [parser-util]
            [rule-validator]
            [clojure.string :as str])
  (:import [data_model Fact Rule]))

(def rule-assign-code ":-")
(def rule-facts-separator-regex #",(?![^\(]*\))")

(defn- new-fact
  "Returns a new Fact representing the fact-string."
  [fact-string]
  (new Fact
    (parser-util/parse-name fact-string)
    (parser-util/parse-params fact-string)))

(defn- parse-rule-facts
  "Returns a set containing all the facts from a rule-string."
  [rule-string]
  (set
    (map new-fact
      (-> rule-string
          (str/split (re-pattern rule-assign-code))
          (nth 1)
          (str/split (re-pattern rule-facts-separator-regex))))))

(defn- parse-rule
  "Converts a rule-string to a Rule,
  or throws an Exception if the rule-string is invalid."
  [rule-string]
  (if-not (rule-validator/valid-rule? rule-string)
    (throw (IllegalArgumentException. (str "Invalid rule: " rule-string))))
  (let [rule (new Rule
              (parser-util/parse-name rule-string)
              (parser-util/parse-params rule-string)
              (parse-rule-facts rule-string))]
    (if (rule-validator/valid-rule-params? rule)
      rule
      (throw (IllegalArgumentException. (str "Invalid rule parameters: " rule-string))))))

(defn parse-rules
  "Returns a set containing all the rules from the database,
  or throws an Exception if the databse can't be parsed."
  [database]
  (->> (parser-util/parse-lines database)
        (remove #(not (str/includes? % rule-assign-code)))
        (map parse-rule)
        set))