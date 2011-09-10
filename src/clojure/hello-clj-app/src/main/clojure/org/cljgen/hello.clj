(ns org.cljgen.hello)

(defn main [args]
  (println "Hello World From Clojure!!!")
  (println "Java main called clojure function with args: "
    (apply str (interpose " " args))))