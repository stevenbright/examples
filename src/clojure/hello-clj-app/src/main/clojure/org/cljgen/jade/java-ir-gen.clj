(ns org.cljgen.jade.java-ir-gen)

(require 'org.cljgen.jade.util)
(use '[org.cljgen.jade.util :only (fail)])

(require 'org.cljgen.jade.naming)
(use '[org.cljgen.jade.naming :only (as-camel-name)])

(require 'org.cljgen.jade.pprint)
(use '[org.cljgen.jade.pprint :only (pprint-ir)])

(require 'org.cljgen.jade.jtypes)
(use '[org.cljgen.jade.jtypes :only (jtype-primitive? jtype-nullable?)])

;;
;; =====================================================================================================================
;;


#_(let [path-base "/Users/alex/proj/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"
        path-base-0 "/home/steve/projects/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"
        path-base-1 "/Users/alex/proj/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"]
    (load-file (str path-base "util.clj"))
    (load-file (str path-base "naming.clj"))
    (load-file (str path-base "jtypes.clj"))
    (load-file (str path-base "pprint.clj"))
    nil)



;;
;; =====================================================================================================================
;;
;; jif = Java Intermediate Form
;;

(def jif-default-order-value 10)

(defn jif-ordered-fields [fields]
  (letfn [(order-of [field] (let [field-sig (second field)]
                              (cond
                                (keyword? field-sig) jif-default-order-value
                                (map? field-sig) (get field-sig :order jif-default-order-value)
                                :else (fail "Unknown field " field " signature: " field-sig))))]
    (sort (fn [l r] (- (order-of l) (order-of r))) fields)))

(defn jif-type-of [field]
  (let [field-type (second field)]
    (cond
      (keyword? field-type) field-type
      (map? field-type) (field-type :type)
      :else (fail "Unknown type record in field " field))))

(defn jif-produce-dto-ir [dto-form]
  (let [fields (dto-form :fields)
        class-name {:value (dto-form :name)}]
    (lazy-cat
      [:public :final :class class-name \{ \newline]
      ;; fields
      (map
        (fn [field] [:private :final (jif-type-of field) (first field) \; \newline])
        (jif-ordered-fields fields))

      [\newline]

      ;; constructor
      [:public class-name \(
       (interpose \, (map (fn [field] [(jif-type-of field) (first field)]) fields))
       \) \{
       (map (fn [field] [:this \. (first field) \= (first field) \; \newline]) fields)
       \}]

      [\newline]

      ;; getters
      (map
        (fn [field]
          (let [field-name (symbol (name (first field)))]
            [:public (symbol (as-camel-name "get" field-name)) \( \) \{
             :return field-name \; \newline
             \} \newline]))
        fields)

      [\}])))

#_(let [dto-form {:name :Person
                  :fields {
                            :id {:type :Long :order 0}
                            :fullName :String
                            :age :int
                            }}]
    ;;(doall (map (fn [x] (println "x =" x)) (vals (dto-form :fields))))
    (apply pprint-ir (jif-produce-dto-ir dto-form)))


