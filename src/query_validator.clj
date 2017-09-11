(ns query-validator)

(def query-regex #"^\w+\((\w+)(, \w+)*\)$")

(defn valid-query?
  "Returns true if the format of query-string is a valid Query format, or false otherwise."
  [query-string]
  (not (nil? (re-matches query-regex query-string))))