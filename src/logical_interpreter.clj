(ns logical-interpreter
  (:require [data-model]
            [fact-parser]
            [rule-parser]
            [query-parser]))

(defn- rule-in-database?
  "Returns true if the Rule represented by query is in rules, and its corresponding
  facts are in the database facts, or false otherwise."
  [facts rules query]
  false
  )

(defn- fact-in-database?
  "Returns true if the Fact represented by query is in facts, or false otherwise."
  [facts query]
  (contains? facts (data-model/query->fact query))
  )

(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [database query-line]
  (try
    (let [facts (fact-parser/get-facts database)
          rules (rule-parser/get-rules database)
          query (query-parser/get-query query-line)]
    (if (fact-in-database? facts query)
        true
        (rule-in-database? facts rules query)
    ))
  (catch Exception e
    (do
      (println (str "Error while parsing input data: " (.getMessage e)))
      nil))))
