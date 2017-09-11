(ns parser-util
  (:require [clojure.string :as str]))

(def whitespace-regex #"\s+")
(def name-regex #"^\w+")
(def params-regex #"\([^\)]*\)")

(defn remove-whitespace
  "Returns a string which equals s after all its whitespace characters have been removed."
  [s]
  (str/replace s whitespace-regex ""))

(defn parse-name
  "Returns the name from a valid input expression.
  A valid input expression must start a non-empty string followed by its parameters,
  which must also be non-empty strings separated by a comma and a space, enclosed within parentheses.
  A valid input expression must have at least one parameter.
  Any text can follow after the parameters."
  [input-expression]
  (re-find name-regex (str/trim input-expression)))

(defn parse-params
  "Returns a list of strings containing the parameters from a valid input expression.
  A valid input expression must start a non-empty string followed by its parameters,
  which must also be non-empty strings separated by a comma and a space, enclosed within parentheses.
  A valid input expression must have at least one parameter.
  Any text can follow after the parameters."
  [input-expression]
  (map str/trim
    (-> (re-find params-regex input-expression)
        (str/replace-first "(" "")
        (str/replace-first ")" "")
        (str/split #","))))

(defn parse-lines
  "Returns a list containing each line from a string,
  where each line has been trimmed and blank lines excluded."
  [s]
  (->> s
      (str/split-lines)
      (map str/trim)
      (remove str/blank?)))
