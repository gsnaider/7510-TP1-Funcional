(ns logical-interpreter
  (:require [data-model]
            [fact-parser]
            [query-parser]))

(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [database query]
  (contains?
    (fact-parser/get-facts database)
    (data-model/query->fact (query-parser/get-query query))))
