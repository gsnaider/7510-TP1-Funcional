(ns query-parser
  (:require [data-model]
            [parser-util]
            [clojure.string :as str])
  (:import [data_model Query]))

(def query-regex #"^\w+\((\w+)(, \w+)*\)$")

(defn- valid-query?
  "Returns true if the query string is a valid query, or false otherwise."
  [query]
  (not (nil? (re-matches query-regex query))))

(defn get-query
  "Returns a Query obtained from parsing a query stirng,
  or throws an Exception if the parsing is not possible."
  [query]
  (if (valid-query? query)
    (new Query (parser-util/get-name query) (parser-util/get-params query))
    (throw (IllegalArgumentException. "Invalid query."))))
