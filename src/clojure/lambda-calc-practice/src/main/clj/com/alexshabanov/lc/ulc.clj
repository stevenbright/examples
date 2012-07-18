(ns
  ^{:doc "Untyped lambda calculus evaluator."
    :author "Alexander Shabanov"}
  com.alexshabanov.lc)

(def
  ^{:doc "Global repository of the named associations."}
  globals (atom {}))


(defmacro l-fun [name args body]
  "Creates new named association of the certain symbol with lambda function.
  E.g. the following correspondence in the standard lambda calculus notation
  pair = λf. λs. λb. b f s
  is equivalent to the following definition
  (lfun pair [b f s] (b f s))"
  `(reset! globals (assoc @globals '~name {:args '~args :body '~body})))

(def ^{:doc "Indentation unit used when printing the intermediate calculations results"}
  indent-unit "  ")


(defn l-eval-form
  "Evaluates the given invocation of the λ-form."
  [indent l-name & args]
  (if (coll? l-name)
    (apply l-eval-form indent l-name)
    (let [h (get @globals l-name)]
      (assert (not (nil? h)) (str "There is no registered definition of " l-name))
      (let [args-mapping (interleave (get h :args) args)
            body (get h :body)]
        (println indent "Evaluating " l-name " = " body "." args)
        (letfn [(substep [form a v]
                  (if (coll? form) (map #(substep % a v) form) (if (= form a) v form)))
                (subst-args [form rest-args-mapping]
                  (let [a (first rest-args-mapping)
                        v (second rest-args-mapping)]
                    (if (nil? a)
                      form
                      (let [result-form (substep form a v)]
                        (println indent "[" a "->" v "] |-> " result-form)
                        (recur result-form (nthnext rest-args-mapping 2))))))
                (eval-form [form]
                  (if (coll? form) (apply l-eval-form (str indent indent-unit) form) form))]
          (eval-form (subst-args body args-mapping)))))))

(defn l-simple-eval [form]
  (println "---" form)
  (let [result (apply l-eval-form "" form)]
    (println "Result of" form "=" result)
    (println)))

;; Helper evaluation macro
(defmacro l-eval [l-sym-name & more]
  `(apply l-eval "" '~l-sym-name '~more))


;;
;; Church boolean functions
;;

(l-fun tru [u v] u)
(l-fun fls [u v] v)
(l-fun and [u v] (u v fls))

(l-fun or [u v] (u tru v))

(l-fun not [u] (u fls tru))

;; Pairs
(l-fun pair [f s b] (b f s))
(l-fun fst [p] (p tru))
(l-fun snd [p] (p fls))

;; Evaluation doesn't work for pairs!
#_(do
    (l-simple-eval '(fst (pair :a :b)))
    nil)

#_(do
    (l-simple-eval '(or tru tru))
    (l-simple-eval '(or tru fls))
    (l-simple-eval '(or fls tru))
    (l-simple-eval '(or fls fls))
    nil)

#_(do
    (l-simple-eval '(and tru tru))
    (l-simple-eval '(and tru fls))
    (l-simple-eval '(and fls tru))
    (l-simple-eval '(and fls fls))
    nil)

#_(do
    (l-simple-eval '(not tru))
    (l-simple-eval '(not fls))
    nil)


(defn main [args]
  (reset! globals {:a 1})
  (println "args = " args))
