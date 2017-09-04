(ns query-parser
  (:require [data-model]
            [clojure.string :as str])
  (:import [data_model Query]))

(def query-regex #"^\w+\((\w+)(, \w+)*\)$")
(def query-name-regex #"^\w+")
(def query-params-regex #"\(.*\)")

(defn- valid-query?
  "Returns true if the query string is a valid query, or false otherwise."
  [query]
  (not (nil? (re-matches query-regex query))))

(defn- get-name
  "Returns the Query name from a query string."
  [query]
  (re-find query-name-regex query))

(defn- get-params
  "Returns a list of strings containing the Query parameters from the query string."
  [query]
  (map str/trim
    (-> (re-find query-params-regex query)
        (str/replace-first "(" "")
        (str/replace-first ")" "")
        (str/split #","))))

(defn get-query
  "Returns a Query obtained from parsing a query stirng,
  or throws an Exception if the parsing is not possible."
  [query]
  (if (valid-query? query)
    (new Query (get-name query) (get-params query))
    (throw (IllegalArgumentException. "Invalid query."))))
