(ns parser-util
  (:require [clojure.string :as str]))

(def name-regex #"^\w+")
(def params-regex #"\(.*\)")

(defn get-name
  "Returns the name from a valid input expression.
  A valid input expression must start a non-empty string followed by its parameters,
  which must also be non-empty strings separated by a comma and a space, enclosed within parentheses.
  A valid input expression must have at least one parameter."
  [input-expression]
  (re-find name-regex input-expression))

(defn get-params
  "Returns a list of strings containing the parameters from a valid input expression.
  A valid input expression must start a non-empty string followed by its parameters,
  which must also be non-empty strings separated by a comma and a space, enclosed within parentheses.
  A valid input expression must have at least one parameter."
  [input-expression]
  (map str/trim
    (-> (re-find params-regex input-expression)
        (str/replace-first "(" "")
        (str/replace-first ")" "")
        (str/split #","))))

(defn get-lines
  "Returns a list containing each line from a string,
  where each line has been trimmed and blank lines excluded."
  [s]
  (->> s
      (str/split-lines)
      (map #(str/trim %))
      (remove str/blank?)))
