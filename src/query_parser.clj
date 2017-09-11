(ns query-parser
  (:require [data-model]
            [parser-util]
            [query-validator]
            [clojure.string :as str])
  (:import [data_model Query]))

(defn parse-query
  "Returns a Query obtained from parsing a query-stirng,
  or throws an Exception if the parsing is not possible."
  [query-string]
  (if (query-validator/valid-query? query-string)
    (new Query
      (parser-util/parse-name query-string)
      (parser-util/parse-params query-string))
    (throw (IllegalArgumentException. "Invalid query."))))
