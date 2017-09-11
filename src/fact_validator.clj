(ns fact-validator)

(def fact-regex #"^\w+\((\w+)(,\w+)*\)\.$")

(defn valid-fact?
  "Returns true if the format of fact-string is a valid fact format, or false otherwise."
  [fact-string]
  (not (nil? (re-matches
    fact-regex
    (parser-util/remove-whitespace fact-string)))))
