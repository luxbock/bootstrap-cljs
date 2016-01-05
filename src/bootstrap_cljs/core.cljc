(ns bootstrap-cljs.core
  (:refer-clojure :exclude [use mask])
  (:require #?(:cljs cljsjs.react-bootstrap)
            [clojure.string :as str]
            [om-tools.dom :as omt]))

(defn kebab-case
  "Converts CamelCase / camelCase to kebab-case"
  [s]
  (str/join "-" (map str/lower-case (re-seq #"\w[a-z]+" s))))

(def bootstrap-tags
  '[Accordion
    Affix
    Alert
    Badge
    Button
    ButtonGroup
    ButtonToolbar
    Carousel
    CarouselItem
    Col
    DropdownButton
    DropdownMenu
    Glyphicon
    Grid
    Input
    Jumbotron
    Label
    MenuItem
    Modal
    Modal.Header
    Modal.Title
    Modal.Body
    Modal.Footer
    ModalTrigger
    Nav
    Navbar
    NavItem
    OverlayTrigger
    PageHeader
    Pager
    PageItem
    Panel
    PanelGroup
    Popover
    ProgressBar
    Row
    SplitButton
    SubNav
    TabbedArea
    Table
    TabPane
    Tooltip
    Well])

#?(:clj
   (defn ^:private gen-bootstrap-inline-fn [tag]
     `(defmacro ~(symbol (kebab-case (str tag)))
        [opts# & children#]
        (let [ctor# '(.createFactory js/React (~(symbol (str ".-" (name tag))) js/ReactBootstrap))]
          (if (om-tools.dom/literal? opts#)
            (let [[opts# children#] (om-tools.dom/element-args opts# children#)]
              (cond
                (every? (complement om-tools.dom/possible-coll?) children#)
                `(~ctor# ~opts# ~@children#)

                (and (= (count children#) 1) (vector? (first children#)))
                `(~ctor# ~opts# ~@(-> children# first flatten))

                :else
                `(apply ~ctor# ~opts# (flatten (vector ~@children#)))))
            `(om-tools.dom/element ~ctor# ~opts# (vector ~@children#)))))))

#?(:clj
   (defmacro ^:private gen-bootstrap-inline-fns []
     `(do ~@(clojure.core/map gen-bootstrap-inline-fn bootstrap-tags))))

#?(:clj
   (gen-bootstrap-inline-fns))
