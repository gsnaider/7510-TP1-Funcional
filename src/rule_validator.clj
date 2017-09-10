(ns rule-validator)

(def rule-regex #"^\w+\((\w+)(, \w+)*\) :- (\w+\((\w+)(, \w+)*\))(, \w+\((\w+)(, \w+)*\))*\.$")

(defn valid-rule?
  "Returns true if the string rule-line is a valid rule, or false otherwise."
  [rule-line]
  ; TODO: Validate that the params from rule-facts are declared as params from rule.
  (not (nil? (re-matches rule-regex rule-line))))