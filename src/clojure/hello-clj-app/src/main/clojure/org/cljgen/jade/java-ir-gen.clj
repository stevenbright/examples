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


#_(let [path-base "/Users/alex/proj/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"]
    (load-file (str path-base "util.clj"))
    (load-file (str path-base "pgf.clj"))
    (load-file (str path-base "naming.clj"))
    (load-file (str path-base "jtypes.clj"))
    nil)

;;
;; =====================================================================================================================
;;

(def jif-whitespace " ")
(def jif-tab-unit "    ")

(defn jif-print-ir [& more]
  (with-local-vars [add-space false
                    was-newline false
                    tab-count 0]
    (letfn [(print-continous-char [c] (print c) (var-set add-space false))
            (print-newline []
              (print \newline)
              (var-set add-space false)
              (var-set was-newline true))
            (print-char-and-newline [c]
              (print c)
              (print-newline))
            (align []
              (let [aligned @was-newline]
                (if aligned (print (apply str (repeat @tab-count jif-tab-unit))))
                (var-set was-newline false)
                aligned))
            (align-and-print [val]
              (align)
              (print val)
              (var-set add-space false))]
      ;; iterate over the forms and align
      (doseq [form more]
        (cond
          (or (symbol? form) (keyword? form)) (do
                                                (if @add-space (align-and-print jif-whitespace))
                                                (align-and-print (name form))
                                                (var-set add-space true))
          (char? form) (cond
                         (= form \{) (do
                                       (if-not (align) (print jif-whitespace))
                                       (var-set tab-count (inc @tab-count))
                                       (print-char-and-newline \{))
                         (= form \newline) (print-newline)
                         (= form \}) (do
                                       (var-set tab-count (dec @tab-count))
                                       ;; validate tab-count
                                       (if (< @tab-count 0) (fail "While printing " more ", tab-count=" @tab-count))
                                       ;;(print "/* tab-count =" @tab-count ", was-newline =" @was-newline "*/")
                                       (align-and-print \})
                                       (print-newline))
                         (= form \.) (print-continous-char \.)
                         (= form \() (print-continous-char \()
                         (= form \tab) (fail "Tabs are auto-generated hence unexpected in " more)
                         :else (print form))
          (string? form) (do (align-and-print \") (print form) (print \"))
          (nil? form) nil ; do nothing
          :else (fail "Unknown form " form " in " more))))))

#_(dosync
    (let [desired-ir-form [
      :public :final :class :Person \{
        :public :static :void :main \( :String \[ \] :args \) \{
          :System \. :out \. :println \( "Hello, world" \) \; \newline
        \}
      \}
      ]]
      (apply jif-print-ir desired-ir-form)))

;;
;; =====================================================================================================================
;;
;; jif = Java Intermediate Form
;;

(def jif-default-order-value 10)

(defn jif-ordered-fields [dto-form]
  (letfn [(order-of [field] (let [field-sig (second field)]
                              (cond
                                (keyword? field-sig) jif-default-order-value
                                (map? field-sig) (get field-sig :order jif-default-order-value)
                                :else (fail "Unknown field " field " signature: " field-sig))))]
    (sort (fn [l r] (- (order-of l) (order-of r))) (dto-form :fields))))




;;
;; =====================================================================================================================
;;

#_(dosync
    (let [dto-form {:name "Person"
                    :fields {
                              "id" {:type :Long :order 0}
                              "fullName" :String
                              "age" :int
                              }
                    }]
      (println dto-form)
      (println "ordered = " (jif-ordered-fields dto-form))
      (doseq [f (dto-form :fields)]
        (println "  f = " f))))

