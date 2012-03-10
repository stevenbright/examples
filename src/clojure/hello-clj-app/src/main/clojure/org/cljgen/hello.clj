(ns org.cljgen.hello)

(defn main [args]
  (println "Hello World From Clojure!!!")
  (let [a "Rob Rikes"]
    (println "name is " a))
  (println "Java main called clojure function with args: "
    (apply str (interpose " " args))))

(main ["1" "2"])

(defmacro sql-expand
  "Transforms nested s-expressions into SQL, for use in WHERE or HAVING clauses.

  e.g.: (sql-expand (and (> foo 3)
                        (< bar 4)))

     -> '(foo > 3 AND bar < 4)'

 Nested clauses:

      (sql-expand (or (and (> foo 3) (< bar 4))
                      (> baz 6)))

      -> '((foo > 3 AND bar < 4) OR baz > 6)'

 Embedded arbitrary SQL:
         (sql-expand (and (> foo 3)
                          (< bar \"(SELECT max(foo) + 10 FROM bar)\")))
      -> '(foo > 3 AND bar < (SELECT max(foo) + 10 FROM bar))'
"
  [form]
  (let [head (first form)]
    (if (contains? '(and or) head)
      `(str "(" (sql-expand ~(second form)) " "
         ~(.toUpperCase (str head)) " "
         (sql-expand ~(last form)) ")")
      (str (second form) " " head " " (last form)))))
