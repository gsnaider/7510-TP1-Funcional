(ns query-parser
  (:require [data-model]
            [parser-util]
            [query-validator]
            [clojure.string :as str])
  (:import [data_model Query]))

(defn get-query
  "Returns a Query obtained from parsing a query stirng,
  or throws an Exception if the parsing is not possible."
  [query]
  (if (query-validator/valid-query? query)
    (new Query (parser-util/get-name query) (parser-util/get-params query))
    (throw (IllegalArgumentException. "Invalid query."))))
