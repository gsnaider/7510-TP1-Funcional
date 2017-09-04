(ns data-model)

(defrecord Fact [name params])

(defrecord Query [name params])

(defn query->fact
  "Returns a fact with the same name and arguments as query."
  [query]
  (new Fact (:name query) (:params query)))
