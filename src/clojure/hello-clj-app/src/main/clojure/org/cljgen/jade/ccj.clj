(ns org.cljgen.jade.ccj)

(require 'org.cljgen.jade.util)
(use '[org.cljgen.jade.util :only (fail)])

(require 'org.cljgen.jade.naming)
(use '[org.cljgen.jade.naming :only (as-camel-name)])

(require 'org.cljgen.jade.pprint)
(use '[org.cljgen.jade.pprint :only (pprint-ir)])

;;
;; =====================================================================================================================
;;


#_(let [path-base "/Users/alex/proj/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"
        path-base-0 "/home/steve/projects/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"
        path-base-1 "/Users/alex/proj/java-maven-tests/src/clojure/hello-clj-app/src/main/clojure/org/cljgen/jade/"]
    (load-file (str path-base "util.clj"))
    (load-file (str path-base "naming.clj"))
    (load-file (str path-base "pprint.clj"))
    nil)

;;
;; =====================================================================================================================
;; Helper methods
;;


(defn multiline-ir-comments [& lines]
  (lazy-cat
    [{:subst "/**"} \newline]
    (map (fn [line] [{:subst (str " * " line)} \newline]) lines)
    [{:subst " */"} \newline]))



;;
;; =====================================================================================================================
;; CCJ definition utility
;;



(defmacro def-ccj-class [class-name & rest]
  `(list ~class-name ~@rest))

#_(println (def-ccj-class Object 1 2 3))
#_(println (macroexpand-1 '(def-ccj-class Object 1 2 3)))


#_(defn pp-f1 [u]
    (println (def-ccj-class Object [:method u])))
#_(pp-f1 'Code) ; => (java.lang.Object [:method Code])

(def-ccj-class Object
  [:method 'hashCode])




(def ccj-objects-registry
  [{:name 'Object
    :methods [{:name 'getClass :returns 'Class}
              {:name 'hashCode :returns :int}
              {:name 'toString :returns 'String}]
    :root true}

   {:name 'Class
    :extends 'Object
    :implements ['Serializable]}

   {:name 'String
    :extends 'Object
    :implements ['Serializable 'Comparable 'CharSequence]
    :fields [{:name 'hash :type :int :modifiers :private}
             {:name 'length :type :int :modifiers [:private :final]}
             {:name 'value :type :CharArray :modifiers [:private :final]}]
    :modifiers :final}

   {:name 'Comparable
    :methods [{:name 'compareTo :arguments ['Object 'o] :returns :int}]
    :interface true}

   {:name 'CharSequence
    :methods [{:name 'length :returns :int}
              {:name 'charAt :arguments [:int 'index] :returns :char}
              {:name 'subSequence :arguments [:int 'pos :int 'count]}]
    :interface true}

   {:name 'Serializable :interface true}])

#_(println "ccj-objs #" (count ccj-objects-registry))



(defn ccj-scan-root [ccj-objects]
  (let [root-objects (filter #(true? (get % :root)) ccj-objects)]
    (if-not (= 1 (count root-objects)) (fail "Too few root objects in " ccj-objects))
    (first root-objects)))

#_(println (ccj-scan-root ccj-objects-registry))


(defn ccj-symbol-type-name [ccj-symbol-type]
  (symbol
    ;; prefix may go here in quotes
    (str "Jem" (name ccj-symbol-type))))

(defn ccj-type-ref [ccj-type]
  (cond
    (keyword? ccj-type) ccj-type
    (symbol? ccj-type) [:struct (ccj-symbol-type-name ccj-type) \*]
    ;;(nil? ccj-type) :void
    :else (fail "Unknown CCJ type " ccj-type)))



(defn ccj-arg-list
  ([] nil)
  ([ccj-type ccj-name] [[(ccj-type-ref ccj-type) ccj-name]])
  ([ccj-type ccj-name & rest] (lazy-cat (ccj-arg-list ccj-type ccj-name) (apply ccj-arg-list rest))))


(defn ccj-gen-vmt-methods [methods]
  (map
    (fn [method]
      [(ccj-type-ref (method :returns)) \space \( \* (method :name) \)
       \( (interpose \, (lazy-cat [[:void \* :ps]] (apply ccj-arg-list (method :arguments)))) \)
       \; \newline])
    methods))

(defn ccj-gen-vmt-root [ccj-object]
  (if-not (ccj-object :root) (fail "Root object VMT expected to be generated here"))
  [[:int 'vmtOffset \; \newline]
   [(ccj-type-ref 'Class) 'klass \; \newline]
   [\newline]
   (ccj-gen-vmt-methods (ccj-object :methods))
   [\newline]])

(defn ccj-find-definition [ccj-sym ccj-class-repo]
  (let [result (filter #(= ccj-sym (get % :name)) ccj-class-repo)]
    (if (= (count result) 1)
      (first result)
      (fail "Class repository expected to contain at least one " ccj-sym))))

#_(println (ccj-find-definition 'Object ccj-objects-registry))



(defn ccj-gen-struct-definition [ccj-sym ccj-class-repo]
  (let [ccj-def (ccj-find-definition ccj-sym ccj-class-repo)
        parent-sym (ccj-def :extends)]
    [(multiline-ir-comments (str "Struct definition for " ccj-sym))
     :struct (ccj-symbol-type-name ccj-sym) \{
     ;; extends
     (if parent-sym [:struct (ccj-symbol-type-name parent-sym) 'parent \; \newline])
     ;; implements
     ;; TODO: infer interface impl name
     (map
       (fn [interface-ref]
         [:struct (ccj-symbol-type-name interface-ref) (symbol (str "impl" (name interface-ref) )) \; \newline])
       (ccj-def :implements))
     ;; TODO: infer self VMT name
     [:struct (ccj-symbol-type-name (symbol (str ccj-sym "Vmt"))) \* 'vmt \; \newline]
     (map (fn [field] [(ccj-type-ref (field :type)) (field :name) \; \newline]) (ccj-def :fields))
     \}]))

#_(pprint-ir
    (ccj-gen-struct-definition 'Object ccj-objects-registry))

#_(pprint-ir
    (ccj-gen-struct-definition 'String ccj-objects-registry))





#_(pprint-ir
    (multiline-ir-comments "VMT for object" "Given as a sample")
    (ccj-gen-vmt-root (ccj-scan-root ccj-objects-registry))
    #_(multiline-ir-comments "VMT for object" "Sample #2")
    #_(ccj-gen-vmt-root {:name 'Object
                         :methods [{:name 'findRob :arguments [:int 'a 'String 'b] :returns 'Rob}
                                   {:name 'toString :returns 'String}]
                         :root true})
    [\newline])



;;
;; =====================================================================================================================
;;

#_(do
    (pprint-ir
      (multiline-ir-comments "Generated by CCJ" "Do not modify this file")
      [{:subst "#include <stdio.h>"} \newline]
      [{:subst "#include <stdlib.h>"} \newline]
      [{:subst "#include <string.h>"} \newline]
      [\newline]))

