(ns query-parser
  (:require [data-model]
            [clojure.string :as str])
  (:import [data_model Query]))

(defn get-query
  "Returns a Query obtained from parsing a query stirng,
  or throws an Exception if the parsing is not possible."
  [query]
  nil)
