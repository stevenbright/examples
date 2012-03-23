(ns org.cljgen.jade.naming)

(defn as-camel-name
  "Utility function that transform the given name to the approapriate string sequence in the camel style"
  [& more]
  (with-local-vars [is-first true]
    (apply str (map (fn [val] (let [v (str val)]
                                (if-not (empty? v)
                                  (str
                                    ;; first character
                                    ((if @is-first #(Character/toLowerCase %) #(Character/toUpperCase %)) (first v))
                                    ;; rest of the string
                                    (do (var-set is-first false) (if (> (count v) 1) (.substring v 1)))))))
                 more))))
