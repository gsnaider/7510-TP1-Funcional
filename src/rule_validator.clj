(ns rule-validator)

(def rule-regex #"^\w+\((\w+)(,\w+)*\):-(\w+\((\w+)(,\w+)*\))(,\w+\((\w+)(,\w+)*\))*\.$")

(defn valid-rule?
  "Returns true if the format of rule-string is a valid Rule format, or false otherwise."
  [rule-string]
  (not (nil? (re-matches 
    rule-regex 
    (parser-util/remove-whitespace rule-string)))))

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