(ns org.cljgen.astgen)

;; load file, then do:
;; (ns org.cljgen.astgen)

(def p-person-form
  '(:ast
     (:interface Person
                 (:method String getName)
                 (:method int getAge))
     (:interface PersonDao
                 (:method long save [Person person])
                 (:method Person findById [long id])
                 (:method Person findAll [int offset int limit]))))

(defn fail [& more]
  (throw (RuntimeException. (apply str more))))

(defn assert-same [expected actual form]
  (if-not (= expected actual)
    (fail "Expected " expected " instead of " actual " in form " form)))

(defn property-name [prefix accessor-symbol]
  (let [accessor-str (str accessor-symbol)
        prefix-len (count prefix)]
    (if-not (.startsWith accessor-str prefix) (fail "Accessor " accessor-str " expected to begin with " prefix))
    (str (Character/toLowerCase (.charAt accessor-str prefix-len)) (.substring accessor-str (inc prefix-len)))))

(def id-field-name "id")

(defn dto-form-to-field-form [dto-form]
  (assert-same :interface (first dto-form) dto-form)
  (with-local-vars [result nil has-id false]
    (var-set result
      (concat (list :dto (second dto-form))
        (map
          (fn [dto-method]
            ;; check leading marker keyword
            (assert-same :method (first dto-method) dto-method)

            (let [s (count dto-method)]
              ;; validate size
              (cond
                (= s 3) nil
                (= s 4) (if-not (= 0 (count (nth dto-method 3)))
                          (fail "Method " dto-method " expected to have empty arguments list"))
                :else (fail "Form " dto-method " expected to have 3 or 4 elements")))

            ;; TODO: check we have only three methods
            (let [prop-name (property-name "get" (nth dto-method 2))]

              ;; ID presence check
              (if (= id-field-name prop-name)
                (var-set has-id true))

              ;; return field presentation
              (list :field (second dto-method) prop-name)))
          ;; sequence of :method forms
          (rest (rest dto-form)))))

    ;; add "ID" field of unknown type
    (if-not @has-id
      (var-set result (concat @result (list (list :field nil id-field-name)))))

    @result))

#_(dto-form-to-field-form '(:interface Person (:method String getName) (:method int getAge)))

;;;   ->
;;; (:dto Person (:field String "name") (:field int "age"))




;;
;; Printing
;;

(def tab-count (ref 0))
(def was-newline (ref false))
(def tab-unit "    ")
(defn j-pr-reset
  "Resets printing facility"
  {:static true}
  []
  (ref-set tab-count 0)
  (ref-set was-newline false))

(defn j-pr
  "Printing facility for java programming language"
  {:static true}
  [& more]
  (letfn [(align []
            (let [aligned @was-newline]
              (if aligned (print (apply str (repeat @tab-count tab-unit))))
              (ref-set was-newline false)
              aligned))
          (align-and-print [val]
            (align)
            (print val))
          (put-newline []
            (print \newline)
            (ref-set was-newline true))]
    (doseq [elem more]
      (cond
        (char? elem) (do
                       ;; update tab-count
                       (cond
                         (= elem \tab) (fail "Tab is auto-applied hence unexpected in " more)
                         (= elem \newline) (put-newline)
                         (= elem \{) (do
                                       ;; print space before open block if it isn't first in line
                                       (if-not (align) (print \space))
                                       (ref-set tab-count (inc @tab-count))
                                       (print \{)
                                       (put-newline))
                         (= elem \}) (do
                                       (ref-set tab-count (dec @tab-count))
                                       ;; validate tab-count
                                       (if (< @tab-count 0) (fail "While printing " more ", tab-count=" @tab-count))
                                       ;; align, print '\{' and newline
                                       (align-and-print \})
                                       (put-newline))
                         ;; just print element itself
                         :else (align-and-print elem)))
        (string? elem) (align-and-print elem)
        (number? elem) (align-and-print (str elem)) ;; TODO: java-specific conversion should go here
        (symbol? elem) (align-and-print (str elem))
        :else (fail "Don't know how to print " elem)))))

#_(dosync
    (j-pr-reset)
    (j-pr "json:" \{)
    (j-pr "fld:" "12" \; \newline)
    (j-pr "field:" \{ "abc:34" \; \newline \})
    (j-pr \})
    nil)


;;
;; concrete java form printers
;;

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



(defn jfp-single-comment [^String comment-text]
  (j-pr \newline "// " comment-text \newline))

(defn jfp-simple-to-string [class-name fields]
  (j-pr "@Override" \newline "public String toString()" \{
    "final StringBuilder builder = new StringBuilder()" \; \newline)
  (j-pr "builder.append" \( \" class-name "#{" \" \) \; \newline)
  (doseq [field fields]
    (j-pr "builder.append" \( \" " " (field :name) ": " \" \) ".append" \( (field :name) \) \; \newline))
  (j-pr "builder.append" \( \" " }" \" \) \; \newline)
  (j-pr "return builder.toString()" \; \newline \}))

(defn jfp-simple-hashcode [fields]
  (j-pr "@Override" \newline "public int hashCode()" \{)
  (j-pr "int result = 0" \; \newline)
  (doseq [field fields]
    (j-pr "// result = 31 * result + " (field :name) ".hashCode()" \; \newline))

    (j-pr "return result" \; \newline \}))

(defn jfp-simple-getters [fields]
  (doseq [field fields]
    (j-pr "public final " (field :type) \space (as-camel-name "get" (field :name)) "()" \{
      "return " (field :name ) \; \newline
      \})))

(defn jfp-simple-constructor [class-name fields]
  (j-pr "public " class-name "(")
  ;; arglist
  (apply j-pr (interpose ", " (map (fn [field] (str (field :type) " " (field :name))) fields)))
  (j-pr \) \{)
  ;; initialization
  (doseq [field fields]
    (j-pr "this." (field :name) " = " (field :name) \; \newline))
  (j-pr \}))

(defn jfp-simple-final-class-vars [fields]
  (doseq [field fields]
    (j-pr "private final " (field :type) \space (field :name) \; \newline)))

(defn jfp-dto [dto-form]
  ;; class declaration
  (let [class-name (str (second dto-form) "Impl")
        fields (rest (rest dto-form))]
    (j-pr "public final class " class-name \{)

    (jfp-single-comment "Class variables")
    (jfp-simple-final-class-vars fields)

    (jfp-single-comment "Public constructor")
    (jfp-simple-constructor class-name fields)

    (jfp-single-comment "Getters")
    (jfp-simple-getters fields)

    ;; hashCode
    (j-pr \newline)
    (jfp-simple-hashcode fields)

    ;; toString
    (j-pr \newline)
    (jfp-simple-to-string class-name fields)

    ;; closing class body block
    (j-pr \})))


;;
;; === Test forms ===
;;

#_(dosync
  (let [dto-form-1 '(:dto Person {:type String :name "fullName"} {:type int :name age} {:type long :name id})]
    (ref-set tab-count 0)
    (ref-set was-newline false)

    (jfp-dto dto-form-1)))
