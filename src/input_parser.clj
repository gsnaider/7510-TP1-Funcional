(ns input-parser
  (:require [data-model]
            [clojure.string :as str])
  (:import [data_model Fact]))

(def rule-assign-code ":-")
(def fact-regex #"^\w+\((\w+)(, \w+)*\)\.$")
(def fact-name-regex #"^\w+")
(def fact-params-regex #"\(.*\)")

(defn- get-lines
  "Returns a list containing each trimmed line from database, excluding blank lines."
  [database]
  (->> database
      (str/split-lines)
      (map #(str/trim %))
      (remove str/blank?)))

(defn- valid-fact?
  "Returns true if the string fact-line is a valid fact, or false otherwise."
  [fact-line]
  (not (nil? (re-matches fact-regex fact-line))))

(defn- get-name
  "Returns the Fact name from the fact-line."
  [fact-line]
  (re-find fact-name-regex fact-line))

(defn- get-params
  "Returns a list of strings containing the Fact parameters from the fact-line."
  [fact-line]
  (map str/trim
    (-> (re-find fact-params-regex fact-line)
        (str/replace-first "(" "")
        (str/replace-first ")" "")
        (str/split #","))))

(defn- line->fact
  "Converts an input string line to a Fact, or throws an Exception if the conversion is not possible."
  [fact-line]
  (if (valid-fact? fact-line)
    (new Fact (get-name fact-line) (get-params fact-line))
    (throw (IllegalArgumentException. "Invalid fact."))))

(defn get-facts
  "Returns a list containing all the facts from the database,
  or throws an Exception if the databse can't be parsed."
  [database]
  (->> (get-lines database)
        (remove #(str/includes? % rule-assign-code))
        (map #(line->fact %))))