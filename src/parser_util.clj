(ns parser-util
  (:require [clojure.string :as str]))

(defn get-lines
  "Returns a list containing each line from a string,
  where each line has been trimmed and blank lines excluded."
  [s]
  (->> s
      (str/split-lines)
      (map #(str/trim %))
      (remove str/blank?)))
