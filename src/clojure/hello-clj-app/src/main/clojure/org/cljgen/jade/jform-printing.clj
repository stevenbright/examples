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
  (doseq [field fields]
    (pgf "builder.append" \( \" " " (field :name ) ": " \" \) ".append" \( (field :name ) \) \; \newline))
  (pgf "builder.append" \( \" " }" \" \) \; \newline)
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
    (pgf "// result = 31 * result + " (field :name ) ".hashCode()" \; \newline))

  (pgf "return result" \; \newline \}))



(defn jfp-simple-getters [fields]
  (doseq [field fields]
    (pgf "public final " (field :type ) \space (as-camel-name "get" (field :name )) "()" \{
      "return " (field :name ) \; \newline
      \})))



(defn jfp-simple-constructor [class-name fields]
  (pgf "public " class-name "(")
  ;; arglist
  (apply pgf (interpose ", " (map (fn [field] (str (field :type ) " " (field :name ))) fields)))
  (pgf \) \{)
  ;; initialization
  (doseq [field fields]
    (pgf "this." (field :name ) " = " (field :name ) \; \newline))
  (pgf \}))



(defn jfp-simple-final-class-vars [fields]
  (doseq [field fields]
    (pgf "private final " (field :type ) \space (field :name ) \; \newline)))




;;
;; Complex form printer
;;

(defn jfp-dto [dto-form]
  ;; class declaration
  (let [class-name (str (second dto-form) "Impl")
        fields (rest (rest dto-form))]
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

    ;; toString
    (pgf \newline)
    (jfp-simple-to-string class-name fields)

    ;; closing class body block
    (pgf \})))


;;
;; === Test forms ===
;;

#_(do
    (load "./util.clj"))

#_(dosync
    (let [dto-form-1 '(:dto Person {:type String :name "fullName"} {:type int :name age} {:type long :name id})]
      (pgf-reset)
      (jfp-dto dto-form-1)))
