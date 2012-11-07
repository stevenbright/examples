(ns ziptest
  [:require [clojure.zip :as z]])

(defn test-inmap-editing []
  (let [src [1 '(a b c) 2]
        srczip (z/seq-zip (seq src))]
    [(-> srczip z/down z/node)
     (-> srczip z/down z/right z/node)
     (-> srczip z/down z/right z/down z/right z/remove z/up z/node)]))

