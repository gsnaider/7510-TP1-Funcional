(ns data-model)

(defrecord Fact [name params])

(defrecord Rule [name params facts])

(defrecord Query [name params])

(defn query->fact
  "Returns a fact with the same name and arguments as query."
  [query]
  (new Fact (:name query) (:params query)))

(defn- names-match?
  "Returns true if the name from rule matches the name from query, or false otherwise."
  [rule query]
    (= (:name rule) (:name query)))

(defn- params-match?
  "Returns true if the number of params from rule match the number of params from query, or false otherwise."
  [rule query]
    (= (count (:params rule)) (count (:params query))))

(defn rule-equals-query?
  "Returns true if rule has the same name and number of parameters as query, or false otherwise."
  [rule query]
  (and 
    (names-match? rule query)
    (params-match? rule query)))