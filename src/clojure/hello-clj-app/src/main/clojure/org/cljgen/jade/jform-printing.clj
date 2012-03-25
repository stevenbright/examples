(ns org.cljgen.jade.jform-printing)

(require 'org.cljgen.jade.util)
(use '[org.cljgen.jade.util :only (fail)])

(require 'org.cljgen.jade.pgf)
(use '[org.cljgen.jade.pgf :only (pgf pgf-reset)])

(require 'org.cljgen.jade.naming)
(use '[org.cljgen.jade.naming :only (as-camel-name)])

(require 'org.cljgen.jade.jtypes)
(use '[org.cljgen.jade.jtypes :only (jtype-primitive? jtype-nullable?)])



;;
;; primitive forms printing
;;

(defn jfp-single-comment [^String comment-text]
  (pgf \newline "// " comment-text \newline))



(defn jfp-simple-to-string [class-name fields]
  (pgf "@Override" \newline "public String toString()" \{
    "final StringBuilder builder = new StringBuilder()" \; \newline)
  (pgf "builder.append" \( \" class-name "#{" \" \) \; \newline)
  (with-local-vars [next false]
    (doseq [field fields]
      (pgf "builder.append" \( \"
        (if @next ", " "") (field :name )
        ": " \" \) ".append" \( (field :name ) \) \; \newline)
      (var-set next true)))
  (pgf "builder.append" \( \" "}" \" \) \; \newline)
  (pgf "return builder.toString()" \; \newline \}))


(defn jfp-calc-hashcode [var type]
  (cond
    (jtype-nullable? type) (pgf \( var " != null ? " var ".hashCode() : 0" \))
    (jtype-primitive? type) (cond
                              (= type :int) (pgf var)
                              (= type :long) (pgf "(int) (" var " ^ (" var " >>> 32))")
                              :else (fail "Unsupported primitive type " type))
    :else (fail "Unsupported type " type)))

#_(dosync
    (jfp-calc-hashcode "a" :int) (println)
    (jfp-calc-hashcode "a" :String) (println)
    (jfp-calc-hashcode "a" :long) (println)
    (println "---"))


(defn jfp-simple-hashcode [fields]
  (pgf "@Override" \newline "public int hashCode()" \{)
  (pgf "int result = 0" \; \newline)
  (doseq [field fields]
    (pgf "result = 31 * result + ")
    (jfp-calc-hashcode (field :name) (field :type))
    (pgf \; \newline))
  ;; result
  (pgf "return result" \; \newline \}))

#_(dosync
    (jfp-simple-hashcode [{:name "fullName" :type :String} {:name 'age :type :int}]))




(defn jfp-calc-not-equals [lhs-expr rhs-expr type]
  (cond
    ;; if (id != null ? !id.equals(person.id) : person.id != null) return false;
    (jtype-nullable? type) (pgf \( lhs-expr " != null ? !" lhs-expr ".equals" \( rhs-expr \) " : "
                             rhs-expr " != null" \))
    (jtype-primitive? type) (pgf \( lhs-expr " != " rhs-expr \))
    :else (fail "Unsupported type " type)))

(defn jfp-simple-equals [class-name fields]
  (pgf "@Override" \newline "public boolean equals(Object o)" \{
    "if (this == o) return true" \; \newline
    "if (o == null || getClass() != o.getClass()) return false" \; \newline
    \newline
    "final " class-name " rhs = " \( class-name \) " o" \; \newline
    \newline)
  (doseq [field fields]
    (pgf "if ")
    (jfp-calc-not-equals (field :name) (str "rhs." (field :name)) (field :type))
    (pgf " return false" \; \newline))
  ;; result
  (pgf \newline "return true" \; \newline \}))



(defn jfp-simple-getters [fields]
  (doseq [field fields]
    (pgf "public final " (name (field :type)) \space (as-camel-name "get" (field :name)) "()" \{
      "return " (field :name ) \; \newline
      \})))



(defn jfp-simple-constructor [class-name fields]
  (pgf "public " class-name "(")
  ;; arglist
  (apply pgf (interpose ", " (map (fn [field] (str (name (field :type)) " " (field :name))) fields)))
  (pgf \) \{)
  ;; initialization
  (doseq [field fields]
    (pgf "this." (field :name) " = " (field :name) \; \newline))
  (pgf \}))



(defn jfp-simple-final-class-vars [fields]
  (doseq [field fields]
    (pgf "private final " (name (field :type)) \space (field :name) \; \newline)))



(defn jfp-generated-annotation []
  (pgf "@Generated" \( \" "aeroj" \" \) \newline))

;;
;; Complex form printer
;;

(defn jfp-dto [dto-form]
  ;; class declaration
  (let [class-name (str (second dto-form) "Impl")
        fields (rest (rest dto-form))]
    (jfp-generated-annotation)
    (pgf "public final class " class-name \{)

    (jfp-single-comment "Class variables")
    (jfp-simple-final-class-vars fields)

    (jfp-single-comment "Public constructor")
    (jfp-simple-constructor class-name fields)

    (jfp-single-comment "Getters")
    (jfp-simple-getters fields)

    ;; hashCode
    (pgf \newline)
    (jfp-simple-hashcode fields)

    ;; equals
    (pgf \newline)
    (jfp-simple-equals class-name fields)

    ;; toString
    (pgf \newline)
    (jfp-simple-to-string class-name fields)

    ;; closing class body block
    (pgf \})))


;;
;; === Test forms ===
;;

;;
;; TODO: print forms to certain IR (intermediate representation that then printed).
;;

#_(let [path-base "/Users/alex/proj/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"]
    (load-file (str path-base "util.clj"))
    (load-file (str path-base "pgf.clj"))
    (load-file (str path-base "naming.clj"))
    (load-file (str path-base "jtypes.clj"))
    nil)

#_(dosync
    (let [dto-form {:name "Person"
                    :fields {
                              "id" {:type :Long :order 0}
                              "fullName" :String
                              "age" :int
                              }
                    }
          default-order-val 10]
      (println dto-form)
      (letfn [(order-of [field] (let [field-sig (second field)]
                                  (cond
                                    (keyword? field-sig) default-order-val
                                    (map? field-sig) (get field-sig :order default-order-val)
                                    :else (fail "Unknown field " field " signature: " field-sig))))]
        (println "ordered = " (sort (fn [l r] (- (order-of l) (order-of r))) (dto-form :fields)))
        (doseq [f (dto-form :fields)]
          (println "  f = " f)))))

#_(dosync
    (let [dto-form-1 '(:dto Person
                            {:type :String :name "fullName"}
                            {:type :int :name age}
                            {:type :Long :name id})]
      (pgf-reset)
      (jfp-dto dto-form-1)))
