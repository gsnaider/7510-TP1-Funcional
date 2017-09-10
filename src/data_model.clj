(ns data-model)

(defrecord Fact [name params])

(defrecord Query [name params])

(defrecord Rule [name params facts])

(defn query->fact
  "Returns a fact with the same name and arguments as query."
  [query]
  (new Fact (:name query) (:params query)))
