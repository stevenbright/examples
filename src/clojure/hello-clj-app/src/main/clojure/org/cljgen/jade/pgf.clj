;;
;; pgf stands for 'print generated form'
;;
(ns org.cljgen.jade.pgf)

(require 'org.cljgen.jade.util)
(use '[org.cljgen.jade.util :only (fail)])

;;
;; printing facility
;;

(def tab-count (ref 0))
(def was-newline (ref false))
(def tab-unit "    ")


(defn pgf-reset
  "Resets printing facility"
  {:static true}
  []
  (ref-set tab-count 0)
  (ref-set was-newline false))


(defn pgf
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
     (pgf-reset)
     (pgf "json:" \{)
     (pgf "fld:" "12" \; \newline)
     (pgf "field:" \{ "abc:34" \; \newline \})
     (pgf \})
     nil)
