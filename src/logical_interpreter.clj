(ns logical-interpreter
  (:require [data-model]
            [fact-parser]
            [rule-parser]
            [query-parser])
  (:import [data_model Fact]))

(defn- find-rule
  "Returns the rule in rules with the same name as query, or nil if there's no such rule."
  [rules query]
    (->> rules
      (filter
        (fn[rule]
          (data-model/rule-equals-query? rule query)))
      first))

(defn- instanciate-fact
  "Returns a Fact equal to fact, but with its parameters
  replaced by their corresponding entries from params-map."
  [fact params-map]
    (new Fact
      (:name fact)
      (map #(get params-map %) (:params fact))))

(defn- instanciate-rule-facts
  "Returns the Fact set from rule, but replacing each generic parameter (X, Y, Z, etc.)
  with the corresponding parameter from query."
  [rule query]
  (let [params-map (zipmap (:params rule) (:params query))]
    (->> (:facts rule)
          (map
            (fn[fact]
              (instanciate-fact fact params-map)))
          set)))

(defn- rule-facts-in-database?
  "Returns true if all the facts from rule, evaluated using the parameters from query, are in facts."
  [facts rule query]
    (let [instanciated-rule-facts (instanciate-rule-facts rule query)]
    (every? (fn[fact] (contains? facts fact)) instanciated-rule-facts)))

(defn- rule-in-database?
  "Returns true if the Rule represented by query is in rules,
  and each fact associated to that rule is in facts."
  [facts rules query]
  (if-let [rule (find-rule rules query)]
    (rule-facts-in-database? facts rule query)
    false))

(defn- fact-in-database?
  "Returns true if the Fact represented by query is in facts, or false otherwise."
  [facts query]
  (contains?
    facts
    (data-model/query->fact query)))

(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [database query]
  (try
    (let [facts (fact-parser/parse-facts database)
          rules (rule-parser/parse-rules database)
          query (query-parser/parse-query query)]

  	(if (fact-in-database? facts query)
      true
  		(rule-in-database? facts rules query)))
  (catch Exception e
    (do
      (println (str "Error while parsing input data: " (.getMessage e)))
      nil))))
