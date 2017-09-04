(ns fact-parser
  (:require [data-model]
            [parser-util]
            [clojure.string :as str])
  (:import [data_model Fact]))

(def rule-assign-code ":-")
(def fact-regex #"^\w+\((\w+)(, \w+)*\)\.$")

(defn- valid-fact?
  "Returns true if the string fact-line is a valid fact, or false otherwise."
  [fact-line]
  (not (nil? (re-matches fact-regex fact-line))))

(defn- fact-line->fact
  "Converts an input string line to a Fact,
  or throws an Exception if the conversion is not possible."
  [fact-line]
  (if (valid-fact? fact-line)
    (new Fact
      (parser-util/get-name fact-line) (parser-util/get-params fact-line))
    (throw (IllegalArgumentException. "Invalid fact."))))

(defn get-facts
  "Returns a list containing all the facts from the database,
  or throws an Exception if the databse can't be parsed."
  [database]
  (->> (parser-util/get-lines database)
        (remove #(str/includes? % rule-assign-code))
        (map #(fact-line->fact %))
        (set)))
