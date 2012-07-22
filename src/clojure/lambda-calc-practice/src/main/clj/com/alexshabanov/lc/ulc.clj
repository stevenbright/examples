(ns
  ^{:doc "Untyped lambda calculus evaluator."
    :author "Alexander Shabanov"}
  com.alexshabanov.lc)

(def
  ^{:doc "Global repository of the named associations."}
  globals (atom {}))

;; special characters used when printing
(def lambda-char \u03bb)
(def right-arrow-char \u2192)

(defn form-as-string
  "Converts the given form to the string representation."
  [form]
  (apply str
    (cond
      (map? form) (concat (map #(str lambda-char % ". ") (get form :args)) [(form-as-string (get form :body))])
      (coll? form) (concat ["("] (interpose " " (map #(form-as-string %) form)) [")"])
      :else [form])))

#_(form-as-string '{:args [s z] :body (s (s (s z)))})
#_(form-as-string '{:args [] :body (s (s (s z)))})


(defmacro define-function
  "Creates new named association of the certain symbol with lambda function.
  E.g. the following correspondence in the standard lambda calculus notation
  pair = λf. λs. λb. b f s
  is equivalent to the following definition
  (define-function pair [b f s] (b f s))"
  [name args body]
  `(reset! globals (assoc @globals '~name {:args '~args :body '~body})))


(def ^{:doc "Indentation unit used when printing the intermediate calculations results"}
  indent-unit "  ")



(defn subst
  "Substitutes entity e by using replace function f provided"
  [f e]
  (if (coll? e) (map #(subst f %) e) (f e)))


(defn evaluate
  "Evaluates the given invocation of the λ-function."
  ([form] (evaluate form {}))
  ([form context]
    (cond
      (map? form) form
      ;; function application
      (coll? form) (let [fn-form (evaluate (first form) context)
                         fn-args (next form)
                         func (if (map? fn-form) fn-form (get @globals fn-form))]
                     (println "Evaluating " form " = " (form-as-string func))

                     (if (nil? func)
                       (println "Unknown function " func)
                       (let [args (get func :args)]
                         ;; apply substitutions
                         (let [subst-result
                               (reduce (fn [curform argpair]
                                         (let [[arg-name arg-value] argpair]
                                           (if (= arg-value ::nothing)
                                             curform ; leave form as is if no more arguments left
                                             ;; subst handling
                                             (let [result {:args (next (get curform :args))
                                                           :body (subst (fn [x] (if (= x arg-name) arg-value x)) (get curform :body))}]
                                               ;;(println "q = " arg-name arg-value (get curform :body))
                                               (println "Subst " arg-name "->" arg-value " = " (form-as-string result))
                                               result))))
                                 (concat [func] (partition 2 (interleave args
                                                               (if (< (count args) (count fn-args))
                                                                 (concat fn-args (take (- (count fn-args) (count args)) (repeat ::nothing)))
                                                                 fn-args)))))]
                           (evaluate (if (empty? (get subst-result :args)) (get subst-result :body) subst-result) context)))))
      ;; symbolic form or term
      :else form)))

#_(evaluate '(tru :a :b))
#_(evaluate '(or fls tru))


;;
;; Church boolean functions
;;

(define-function tru [u v] u)
(define-function fls [u v] v)
(define-function and [u v] (u v fls))

(define-function or [u v] (u tru v))

(define-function not [u] (u fls tru))

;; Pairs
(define-function pair [f s b] (b f s))
(define-function fst [p] (p tru))
(define-function snd [p] (p fls))

;; Evaluation doesn't work for pairs!
#_(do
    (evaluate '(fst (pair :a :b)))
    (evaluate '(snd (pair :a :b)))
    nil)

#_(do
    (evaluate '(or tru tru))
    (evaluate '(or tru fls))
    (evaluate '(or fls tru))
    (evaluate '(or fls fls))
    nil)

#_(do
    (evaluate '(and tru tru))
    (evaluate '(and tru fls))
    (evaluate '(and fls tru))
    (evaluate '(and fls fls))
    nil)

#_(do
    (evaluate '(not tru))
    (evaluate '(not fls))
    nil)


(defn main [args]
  (reset! globals {:a 1})
  (println "args = " args))
