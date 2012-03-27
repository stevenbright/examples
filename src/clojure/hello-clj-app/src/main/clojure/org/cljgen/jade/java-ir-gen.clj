(ns org.cljgen.jade.java-ir-gen)

(require 'org.cljgen.jade.util)
(use '[org.cljgen.jade.util :only (fail)])

(require 'org.cljgen.jade.naming)
(use '[org.cljgen.jade.naming :only (as-camel-name)])

(require 'org.cljgen.jade.jtypes)
(use '[org.cljgen.jade.jtypes :only (jtype-primitive? jtype-nullable?)])

;;
;; =====================================================================================================================
;;


#_(let [path-base "/Users/alex/proj/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"
        path-base-0 "/home/steve/projects/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"
        path-base-1 "/Users/alex/proj/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"]
    (load-file (str path-base "util.clj"))
    (load-file (str path-base "pgf.clj"))
    (load-file (str path-base "naming.clj"))
    (load-file (str path-base "jtypes.clj"))
    ;;(load-file (str path-base "java-ir-gen.clj"))
    nil)



;;
;; =====================================================================================================================
;; IR pretty printer
;;

(def pprinter-whitespace " ")
(def pprinter-tab-unit "    ")

(defn pprint-ir [& more]
  (with-local-vars [tab-count 0
                    align-state :none]
    (letfn [(print-aligned [element next-state]
              (cond
                (= @align-state :whitespace) (print pprinter-whitespace)
                (= @align-state :newline) (print (apply str (repeat @tab-count pprinter-tab-unit)))
                (= @align-state :none) nil
                :else (fail "Unexpected align state " align-state " when printing " more))
              (print element)
              (var-set align-state next-state))
            (print-newline []
              (print \newline)
              (var-set align-state :newline))
            (print-char [ch]
              (cond
                (= ch \tab) (fail "Tab is unexpected in " more)
                (= ch \{) (do
                            (print-aligned \{ :dont-care)
                            (print-newline)
                            (var-set tab-count (inc @tab-count)))
                (= ch \}) (do
                            (var-set tab-count (dec @tab-count))
                            (print-aligned \} :dont-care)
                            (print-newline))
                (= ch \newline) (print-newline)
                ;; TODO: special case for unaligned characters
                (or (= ch \.) (= ch \;) (= ch \() (= ch \[)) (do
                                                               (var-set align-state :none)
                                                               (print ch))
                (or (= ch \)) (= ch \]) (= ch \,)) (do
                                                     (var-set align-state :whitespace)
                                                     (print ch))
                :else (print-aligned ch :whitespace)))
            (print-element [element]
              (cond
                (or (keyword? element) (symbol? element)) (print-aligned (name element) :whitespace)
                (char? element) (print-char element)
                (number? element) (print-aligned (str element) :whitespace)
                (string? element) (print-aligned (str \" element \") :none)
                (map? element) (print-mapped-value element) ; map? should go prior to coll? since map IS a coll
                (coll? element) (print-elements element)
                (nil? element) nil ; do nothing
                :else (fail "Unknown element " element " in " more)))
            (print-mapped-value [map-element]
              (let [val (get map-element :subst)]
                (if (nil? val)
                  (print-element (get map-element :value))
                  (print-aligned val (get map-element :align :none)))))
            (print-elements [elements]
              (doseq [element elements]
                (print-element element)))]
      (print-elements more))))

#_(do
    (pprint-ir :public :static :final :class :Person \{
      [:private :int :a \; \newline]
      [:private :int {:value 'bobVar} \; \newline]
      [:private :int :c \= 42 \; \newline]
      [:private :int :ddd \= "Hello, world!" \; \newline]
      [{:subst "// this is a comment"} \newline]
      ;; method
      [\newline
       [:public :void :bar \( \) \{
        :System \. :out \. :println \( "Hi!" \) \; \newline
        \}]
       \newline]

      ;; method
      [\newline
       [:public :void :bar \( :int :a \, :long 'bbb \) \{
        :System \. :out \. :println \( "Hi!" \) \; \newline
        \}]
       \newline]

      ;; main
      [\newline
       [:public :static :void :main \( :String \[ \] :args \) \{
        :System \. :out \. :println \( "Hello, world!" \) \; \newline
        \}]
       \newline]
      \}))

#_(do
    (apply pprint-ir [
                       :public :final :class :Person \{
                       :public :static :void :main \( :String \[ \] :args \) \{
                       :System \. :out \. :println \( "Hello, world" \) \; \newline
                       \}
                       \}
                       ]))

#_(do
    (apply pprint-ir [
                       :public :final :class :Person :implements :Serializable\{
                       \newline
                       :private :static :final :long :serialVersionUID \; \newline
                       \newline
                       :public :static :void :main \( :String \[ \] :args \) \{
                       :System \. :out \. :println \( "Hello, world" \) \; \newline
                       \}
                       \}
                       ]))


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


