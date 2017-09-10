(ns logical-interpreter
  (:require [data-model]
            [fact-parser]
            [rule-parser]
            [query-parser])
  (:import [data_model Fact]))

(defn- find-rule
  "Returns the rule in rules with the same name as query, or nil if there's no such rule."
  [rules query]
    (first (filter (fn[rule](= (:name rule) (:name query))) rules))
  )

(defn- params-match?
  "Returns true if the number of params from rule match the number of params from query, or false otherwise."
  [rule query]
    (= (count (:params rule)) (count (:params query)))
  )

(defn- evaluate-params
  "Returns a set of facts that come from evaluating each X, Y,..
  form rule-params with the concrete params in the map params"
  [rule params-map]
    (set 
    (map
      (fn[fact]
          (new Fact
            (:name fact) 
            (map #(get params-map %) (:params fact))))
      (:facts rule))))

(defn- fact-in-db?
  "Returns true if the Fact represented by Query is in facts, or false otherwise."
  [facts query]
  (contains?
    facts
    (data-model/query->fact query)))

(defn- facts-in-db?
  "Returns true if all the facts from rule, evaluated with the params from query, are in facts."
  [facts rule query]
    (let [params (zipmap (:params rule) (:params query))
          concrete-facts (evaluate-params rule params)]
    (reduce (fn[x y] (and x y)) 
      (map #(fact-in-db? facts %) concrete-facts)
      )
    )
  )

(defn- rule-in-db?
  "Returns true if the Rule represented by query is in rules,
  and each fact associated to that rule is in facts."
	[facts rules query]
  (if-let [rule (find-rule rules query)]
    (if (params-match? rule query)
      (facts-in-db? facts rule query)
      false
    )
    false 
  )
)

(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [database query]
  (try
  	(let [facts (fact-parser/get-facts database)
          rules (rule-parser/get-rules database)
          query (query-parser/get-query query)]

  	(if (fact-in-db? facts query)
  		true
  		(rule-in-db? facts rules query)
    ))
  (catch Exception e
    (do
      (println (str "Error while parsing input data: " (.getMessage e)))
      nil))))
