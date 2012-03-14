(ns org.cljgen.astgen)


(def p-person-form
  '(:ast
     (:interface Person
                 (:method String getName)
                 (:method int getAge))
     (:interface PersonDao
                 (:method long save [Person person])
                 (:method Person findById [long id])
                 (:method Person findAll [int offset int limit]))))

(defn fail [message-seq]
  (throw (RuntimeException. (apply str message-seq))))

(defn assert-same [expected actual form]
  (if-not (= expected actual)
    (fail ["Expected " expected " instead of " actual " in form " form])))

(defn property-name [prefix accessor-symbol]
  (let [accessor-str (str accessor-symbol)
        prefix-len (count prefix)]
    (if-not (.startsWith accessor-str prefix) (fail ["Accessor " accessor-str " expected to begin with " prefix]))
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
                          (fail ["Method " dto-method " expected to have empty arguments list"]))
                :else (fail ["Form " dto-method " expected to have 3 or 4 elements"])))

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

#_(def my-dto-prop-form '(:dto Person (:field String "name" :nullable true) (:field int age) (:field long id)))

#_
(defn print-dto-form [dto-form]
  ;; class declaration
  (let [class-name (str (second dto-form) "Impl")
        fields (rest (rest dto-form))]
    (println "public final class" class-name "{")

    ;; Private properties
    (doseq [field fields]
      (println (str \tab "private final " (nth field 1) " " (nth field 2) ";")))

    ;; newline before ctor
    (println)

    ;; Constructor
    (print (str \tab "public " class-name "("))
    ;; arglist
    (print (apply str (interpose ", " (map (fn [field] (str (nth field 1) " " (nth field 2))) fields))))
    (println ") {")
    ;; initialization
    (print (apply str (interpose ", " (map (fn [field] (str (nth field 1) " " (nth field 2))) fields))))
    (println (str \tab "}"))

    ;; closing class body block
    (print \})))

#_(do (print-dto-form my-dto-prop-form) (println))

(comment

  ;;; AST printer, much more concise than your CodeDOM and perhaps R# :-P
  ;;; Can be easily extended to support other language constructs &
  ;;; several languages simultaneously

  ;; pretty-print dispatch table: type -> printing func correspondence
  (defvar *dispatch* (copy-pprint-dispatch))

  (defun ast-print (x &optional (stream *standard-output*))
    "Print the expression x using AST dispatch rules"
    (write x :pretty t :pprint-dispatch *dispatch* :stream stream))

  (defun ast-print-form (stream form)
    "Print the AST node (form)"
    (if (and (symbolp (first form))
          (get (first form) 'ast-form))
      (funcall (get (first form) 'ast-form) stream form)
      (error "unknown form: ~S" form)))

  ;; set dispatch function for cons type in our pretty-print dispatch table
  (set-pprint-dispatch 'cons #'ast-print-form 0 *dispatch*)

  (defmacro defprinter (name args &body body)
    "Define a printer for specified AST node type"
    (let ((func-name (intern (format nil "PRINT-FORM-~A" name)))
           (item (gensym)))
      `(progn
         (defun ,func-name (stream ,item)
           (destructuring-bind ,args (rest ,item)
             ,@body))
         (setf (get ',name 'ast-form) ',func-name))))

  )