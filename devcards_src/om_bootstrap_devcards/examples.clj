(ns om-bootstrap-devcards.examples)

(defmacro defntraced
  "Define a function with it's inputs and output logged to the console."
  [sym & body]
  (let [[_ _ [_ & specs]] (macroexpand `(defn ~sym ~@body))
        new-specs
        (map
          (fn [[args body]]
            (let [prns (for [arg args]
                         `(js/console.log (str '~arg) "=" (pr-str ~arg)))]
              (list
                args
                `(do
                   (js/console.groupCollapsed (str '~sym " " '~args))
                   ~@prns
                   (let [res# ~body]
                     (js/console.log "=>" (pr-str res#))
                     (js/console.groupEnd)
                     res#)))))
          specs)]
    `(def ~sym (fn ~@new-specs))))

(defmacro sourced-ex [code]
  `(dom/div
     (dom/div ~code)
     (dom/br {:style {:line-height "2px"}})
     (dom/div (dom/pre (dom/code {:class "clojure"} (str '~code))))))

(defmacro sourced-om-ex
  [code]
  `(om/root-card))
