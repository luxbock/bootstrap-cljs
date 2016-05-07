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
    Alert
    Badge
    Breadcrumb
    BreadcrumbItem
    Button
    ButtonGroup
    ButtonInput
    ButtonToolbar
    Carousel
    CarouselItem
    Col
    CollapsibleNav
    Dropdown
    DropdownButton
    Dropdown.Menu
    Dropdown.Toggle
    Glyphicon
    Grid
    Image
    Input
    Jumbotron
    Label
    ListGroup
    ListGroupItem
    MenuItem
    Modal
    ModalBody
    ModalFooter
    ModalHeader
    ModalTitle
    Modal.Body
    Modal.Dialog
    Modal.Footer
    Modal.Header
    Modal.Title
    Nav
    Navbar
    Navbar.Header
    Navbar.Toggle
    Navbar.Brand
    Navbar.Collapse
    Navbar
    NavBrand
    NavDropdown
    NavItem
    Overlay
    OverlayTrigger
    PageHeader
    PageItem
    Pager
    Pagination
    Panel
    PanelGroup
    Popover
    ProgressBar
    ResponsiveEmbed
    Row
    SafeAnchor
    SplitButton
    SplitButton.Toggle
    Tab
    Table
    Tabs
    Thumbnail
    Tooltip
    Well
    Collapse
    Fade
    FormControl.Static])

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
