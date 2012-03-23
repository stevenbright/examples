;;
;; Contains type-related operations
;;
(ns org.cljgen.jade.jtypes)

(require 'org.cljgen.jade.util)
(use '[org.cljgen.jade.util :only (fail)])


;;
;;  === Type traits ===
;;
;; Holds all the type information associated with the generated classes
;;

(def jtype-traits {
                    ;; base class
                    :Object {}

                    ;; primitives
                    :boolean {:primitive true}
                    :int {:primitive true}
                    :long {:primitive true}

                    ;; java.lang.* classes
                    :Number {:extends :Object :abstract true}
                    :Long {:extends :Number :boxed :long}

                    :String {:extends :Object}
                    })

(defn jtype-map [type fn]
  (let [val (jtype-traits type)]
    (if-not type (fail "Type " type " is not supported at the moment"))
    (fn val)))



(defn jtype-primitive? [type]
  (jtype-map type #(contains? % :primitive)))

#_(jtype-primitive? :int)
#_(jtype-primitive? :Long)



(defn jtype-nullable? [type]
  ;; it's sufficient to check whether the object have :extends key
  (jtype-map type #(contains? % :extends)))

#_(jtype-nullable? :String)
#_(jtype-nullable? :int)
