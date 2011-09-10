(ns org.aspm.bridge)

(defn main [args]
  (println "=== ASPM Clojure Bridge ===")
  (println "Java main called clojure function with args: "
    (apply str (interpose " " args))))

;;(main '(1 2 3 4))

(defn dummy []
  '(
     ;; define AST expression capture rules
     (defrule
       ;; method name pattern
       (action entity (:opt quantifier))
       ;; method signature
       (arguments => type)
       ;; action on pattern
       (foo))

     nil)

  ;; define concrete rules
  '(
     ;; save rule
     (
     ;; method name pattern
     (:save entity)
     ;; signature pattern
     (arguments => type-id)
     ;; rules

     nil)

  nil)

  nil)
