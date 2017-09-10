(ns query-validator)

(def query-regex #"^\w+\((\w+)(, \w+)*\)$")

(defn valid-query?
  "Returns true if the query string is a valid query, or false otherwise."
  [query]
  (not (nil? (re-matches query-regex query))))