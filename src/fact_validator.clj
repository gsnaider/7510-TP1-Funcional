(ns fact-validator)

(def fact-regex #"^\w+\((\w+)(, \w+)*\)\.$")

(defn valid-fact?
  "Returns true if the string fact-line is a valid fact, or false otherwise."
  [fact-line]
  (not (nil? (re-matches fact-regex fact-line))))