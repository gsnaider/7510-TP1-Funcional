(ns rule-validator)

(def rule-regex #"^\w+\((\w+)(, \w+)*\) :- (\w+\((\w+)(, \w+)*\))(, \w+\((\w+)(, \w+)*\))*\.$")

(defn valid-rule?
  "Returns true if the string rule-line is a valid rule, or false otherwise."
  [rule-line]
  (not (nil? (re-matches rule-regex rule-line))))

(defn valid-rule-params?
  "Returns true if the parameters declared in the rule name are the same as 
  the parameters in its facts, or false otherwise."
  [rule]
  (= 
    (set (:params rule))
    (->> (:facts rule)
      (map :params)
      flatten
      set)))