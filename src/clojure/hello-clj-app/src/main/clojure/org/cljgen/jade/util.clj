(ns org.cljgen.jade.util)


(defn fail [& more]
  (throw (RuntimeException. (apply str more))))

(defn assert-same [expected actual form]
  (if-not (= expected actual)
    (fail "Expected " expected " instead of " actual " in form " form)))


