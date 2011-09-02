;;
;; differentiates the given form by the given var
;; D[C, x] -> 0
;; D[x, x] -> 1
;; D[(u + v), x] -> D[u, x] + D[v, x]
;; D[(u - v), x] -> D[u, x] - D[v, x]
;; D[u * v, x] -> u * D[v, x] + v * D[u, x]
;; D[u ^ n, x] -> n * u ^ (n - 1) * D[u, x]
;;
(defn differentiate [func-form var]
  (println "in differentiate, form = " func-form ", var = " var)
  (cond
    (number? func-form) 0
    (= func-form var) 1
    (list? func-form) (let [op (first func-form)
                            u (nth func-form 1)
                            v (nth func-form 2)]
                        (cond
                          ;; '+' or '-' operation
                          (or (= op '+) (= op '-))
                            (list op
                              (differentiate u var)
                              (differentiate v var))
                          ;; '+' or '-' operation
                          (= op '*)
                            (list '+
                              (list '* u (differentiate v var))
                              (list '* v (differentiate u var)))
                          ;; '^' - power operation
;                          (= op ''^)
;                            (list '*
;                              (list '* u (differentiate v var))
;                              (list '* v (differentiate u var)))
                          ;; error token
                          :else (str "error op " op " in form " func-form)))
    :else (str "error form = " func-form)))

(println "r = " (differentiate 12 'x))
(println "r = " (differentiate 'x 'x))
(println "r = " (differentiate '(+ x 1) 'x))
(println "r = " (differentiate '(* x 2) 'x))

;; (differentiate '(+ x 1) x)

(def sample-code '(
                    ;; some garbage
                    (barrra dfs dsfgf ew)
                    1
                    2.2
                    ))
