(ns com.alexshabanov.lc)

(defn u-or [u v] (u tru v))



(def globals (atom {}))


(defmacro deflambda [name args body])

(defn tru [u v] u)
(defn fls [u v] v)


(defn main [args]
  (reset! globals {:a 1})
  (println "args = " args))
