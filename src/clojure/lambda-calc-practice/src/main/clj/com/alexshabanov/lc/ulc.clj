(ns com.alexshabanov.lc)

(defn tru [u v] u)
(defn fls [u v] v)

(defn u-or [u v] (u tru v))


(defn main [args]
  (println "args = " args))
