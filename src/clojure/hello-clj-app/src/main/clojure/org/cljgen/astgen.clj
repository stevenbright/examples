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

(def tab-count (ref 0))
(def was-newline (ref false))

(defn j-pr
  "Printing facility for java programming language"
  {:static true}
  [& more]
  (letfn [(align [] (print (apply str (repeat @tab-count \tab))))
          (put-newline [] (print \newline) (ref-set was-newline true))]
    (doseq [elem more]
      (cond
        (char? elem) (do
                       ;; print spaces if it was newline
                       (if @was-newline 
                         (do (align) (ref-set was-newline false)))
                       ;; update tab-count
                       (cond
                         (= elem \tab) (fail "Tab is auto-applied hence unexpected in " more)
                         (= elem \newline) (put-newline)
                         (= elem \{) (do (ref-set tab-count (inc @tab-count)) (println \{) (put-newline))
                         (= elem \}) (do (ref-set tab-count (dec @tab-count)) (align) (println \}) (put-newline))
                         ;; just print element itself
                         :else (print elem))
                       ;; validate tab-count
                       (if (< (deref tab-count) 0) (fail "While printing " more ", tab-count=" @tab-count)))
        (string? elem) (print elem)
        (symbol? elem) (print (str elem))
        :else (fail "Don't know how to print " elem)))))




;; TODO: convenient print function that accepts strings and sequences

(defn print-dto-form [dto-form]
  ;; class declaration
  (let [class-name (str (second dto-form) "Impl")
        fields (rest (rest dto-form))]
    (j-pr "public final class " class-name \space \{)

    ;; Private properties
    (doseq [field fields]
      (j-pr "private final " (nth field 1) " " (nth field 2) \; \newline))

    ;; Constructor
    (j-pr \newline "public " class-name "(")
    ;; arglist
    (apply j-pr (interpose ", " (map (fn [field] (str (nth field 1) " " (nth field 2))) fields)))
    (j-pr \) \space \{)
    ;; initialization
    (apply j-pr (map (fn [field] (str "this." (nth field 2) " = " (nth field 2) \; \newline)) fields))
    (j-pr \})

    ;; getters
    (apply j-pr (map (fn [field] (str "// getter for " (nth field 1) \newline)) fields))

    ;; closing class body block
    (j-pr \})))


;;
;; === Test forms ===
;;

#_(def my-dto-prop-form '(:dto Person (:field String "name" :nullable true) (:field int age) (:field long id)))
#_(dosync
    (ref-set tab-count 0)
    (print-dto-form my-dto-prop-form)
    (println))

