(ns org.aspm.bridge)

(defn main [args]
  (println "=== ASPM Clojure Bridge ===")
  (println "Java main called clojure function with args: "
    (apply str (interpose " " args))))