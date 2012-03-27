(ns org.cljgen.jade.pprint)

(require 'org.cljgen.jade.util)
(use '[org.cljgen.jade.util :only (fail)])



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
                (or (= ch \.) (= ch \;) (= ch \() (= ch \[) (= ch \space)) (do
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
