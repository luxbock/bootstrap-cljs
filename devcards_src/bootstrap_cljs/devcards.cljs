(ns bootstrap-cljs.devcards
  (:require [devcards.system]
            [devcards.core :as dc :include-macros true]
            [clojure.string :as str]
            [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [bootstrap-cljs.core :as bs :include-macros true]
            [om-tools.core :refer-macros [defcomponent]])
  (:require-macros [devcards.core :refer [defcard]]))

(enable-console-print!)

(devcards.core/start-devcard-ui!)

(swap! devcards.system/app-state assoc-in [:base-card-options :heading] false)

;; Run `lein figwheel` and open the browser to http://localhost:3449/index.html

(defn mkdn [& mkd-strs] (dom/div
                          (apply dc/markdown->react mkd-strs)))
(defn react-card [& content] (dom/div content))

(def positions ["left" "top" "bottom" "right"])
(def styles ["default" "primary" "success" "info" "warning" "danger"])

(defn styled-buttons [props]
  (map
   #(bs/button (merge {:bs-style %} props) (str/capitalize %))
   styles))

(defcard buttons-intro
  "# Bootstrap CLJS"
  "The examples here are based off [React Bootstrap
    Components](http://react-bootstrap.github.io/components.html) page. As you
    can see, translating the JSX to idiomatic ClojureScript is rather straight
    forward and I've thus not included all of the examples from the page. /or
    prop-names you can use either camelCase or kebab-case. In the situations
    where the prop is named but has no parameter, the equivalent in CLJS is
    `:prop-name true`.")

(defcard ex-1
  (react-card
   (mkdn
    "## Buttons"
    "### Modify the `:bs-style` prop to create styled buttons"
    "`button-toolbar` can be called either with multiple children, or
        with a sequence of children, so these two should produce the same
        result:")
   (dom/br)
   (bs/button-toolbar
    (map #(bs/button {:bs-style %} (str/capitalize %))
         ["default" "primary" "success" "info" "warning" "danger"]))
   (dom/br)
   (bs/button-toolbar
    (bs/button "Default")
    (bs/button {:bsStyle "primary"} "Primary")
    (bs/button {:bsStyle "success"} "Success")
    (bs/button {:bsStyle "info"} "Info")
    (bs/button {:bsStyle "warning"} "Warning")
    (bs/button {:bsStyle "danger"} "Danger"))
   (dom/br)
   (mkdn "Note that you can use both camelCase or kebab-case for the props.")))

(defcard ex-2
  (react-card
   (mkdn
    "### Different button sizes"
    "`:bs-size \"large\"`:")
   (bs/button-toolbar (styled-buttons {:bs-size "large"}))
   (dom/br)
   (mkdn "`:bs-size \"small\"`:")
   (bs/button-toolbar (styled-buttons {:bs-size "small"}))
   (dom/br)
   (mkdn "`:bs-size \"xsmall\"`:")
   (bs/button-toolbar (styled-buttons {:bs-size "xsmall"}))))

(defcard ex-3
  (react-card
   (mkdn
    "### Block level buttons"
    "Set the `:block` prop to `true`:")
   (dom/div {:class "well" :style {:maxWidth 400}}
            (styled-buttons {:bs-size "large" :block true}))))

(defcard ex-4
  (react-card
   (mkdn
    "### Active / disabled buttons"
    "`:active true` prop:")
   (bs/button-toolbar (styled-buttons {:active true}))
   (dom/br)
   (mkdn "`:disabled true` prop:")
   (bs/button-toolbar (styled-buttons {:disabled true}))))

(defcard ex-5
  (react-card
   (mkdn
    "### Button groups"
    "Use `button-group` to group buttons together:")
   (bs/button-group (styled-buttons {}))
   (dom/br)
   (dom/br)
   (bs/button-group (map #(bs/button (str %)) (range 1 11)))))

(defcard ex-6
  (react-card
   (mkdn "### Dropdown menus")
   (mkdn "Using `dropdown-button` in a `button-group`:")
   (bs/button-group
    (for [style styles]
      (bs/dropdown-button {:title (str/capitalize style) :bs-style style :id (str "a-dropdown-button-" style)}
                          (map #(bs/menu-item {:key (str %)} (str "Link " %)) (range 1 5)))))
   (dom/br)
   (dom/br)
   (mkdn "Using `split-button` in a `button-toolbar`:")
   (bs/button-toolbar
    (for [style styles]
      (bs/split-button {:title (str/capitalize style) :bs-style style :id (str "a-split-button-" style)}
                       (map #(bs/menu-item {:key (str %)} (str "Link " %)) (range 1 5)))))))

(defcard ex-7
  (react-card
   (mkdn "## Panels")
   (mkdn "### Panels with headers")
   (map
    #(bs/panel {:header "Title" :bs-style %} (str/capitalize %))
    styles)))

(defcard ex-8
  (react-card
   (mkdn "### Accordions")
   (bs/accordion
    (map
     #(bs/panel
       {:header (str "Group item #" %)
        :key (str %)}
       "Hidden content")
     (range 1 6)))))

(defcard ex-9
  (react-card
   (mkdn
    "### Tooltips"
    "Positioned `tooltip` component. Hover to view.")
   (bs/button-toolbar
    (map
     #(bs/overlay-trigger
       {:placement %
        :overlay (bs/tooltip {:id "check-this"} "Check this info!")}
       (bs/button "Holy guacamole!"))
     positions))))

(defn link-with-tooltip
  [{:keys [href tooltip content]} owner]
  (om/component
   (bs/overlay-trigger
    {:placement "top"
     :overlay (bs/tooltip {:id "href"} tooltip)
     :delay-show 300
     :delay-hide 150}
    (dom/a {:href href} content))))

(defn link [content tooltip]
  (om/build link-with-tooltip
            {:href "#" :tooltip tooltip :content content}))

(defcard ex-10
  (react-card
   (mkdn "### Links with tooltips")
   (dom/p
    "Tight pants next level keffiyeh you " (link "probably haven't" "Default
    tooltip") " heard of them. Photo booth beard raw denim letterpress vegan
    messenger bag stumptown. Farm-to-table seitan, mcsweeney's fixie sustainable
    quinoa 8-bit american apparel " (link "have a" "Another tooltip") " terry
    richardson vinyl chambray. Beard stumptown, cardigans banh mi lomo
    thundercats. Tofu biodiesel williamsburg marfa, four loko mcsweeney's
    cleanse vegan chambray. A really ironic artisan "
    (link "whatever keytar" "Another one") ", scenester farm-to-table banksy
    Austin " (link "twitter handle" "The last tip!")
    " freegan cred raw denim single-origin coffee viral.")))

(defcard ex-11
  (react-card
   (mkdn
    "## Popovers"
    "Click to view.")
   (bs/button-toolbar
    (map
     #(bs/overlay-trigger
       {:trigger "click"
        :placement %
        :overlay (bs/popover {:title (str "Popover " %) :id "check-this-too"}
                             "Holy guacamole! " (dom/b "Check this info!"))}
       (bs/button "Hole guacamole!"))
     positions))))

(defcard ex-12
  (react-card
   (mkdn
    "## Progress bars"
    "Default bar:")
   (bs/progress-bar {:now 60})
   (dom/hr)
   (mkdn "Add a label with `:label`. The following keys are interpolated with
    the current values: `%(min)s`, `%(max)s`, `%(now)s`, `%(percent)s`,
    `%(bsStyle)s`")
   (bs/progress-bar {:now 60 :label "%(now)s / %(max)s"})
   (dom/hr)
   (mkdn "Progress bars can be styled using the usual `:bs-style`
    prop. Additional styling is provided by the `:striped` and `:active`
    props.")
   (dom/div
    (map #(bs/progress-bar {:now %1 :striped %2 :active %3 :bs-style %4})
         (range 10 101 10)
         (cycle [false true])
         (cycle [false false false true])
         (cycle ["success" "warning" "danger"])))
   (mkdn "You can stack `progress-bar`'s by nesting them:")
   (bs/progress-bar
    (map #(bs/progress-bar {:bs-style %1 :now %2 :key %3})
         ["success" "warning" "danger"]
         [35 20 10]
         (range 1 4)))))

(defcard ex-13
  (react-card
   (mkdn
    "## Navigation"
    "### Navs")
   (bs/nav
    {:bs-style "pills"
     :active-key 1
     :on-select #(js/alert (str % " selected"))}
    (bs/nav-item {:key 1 :href "#!/bootstrap_cljs_devcards/ex-13"} "NavItem 1 content")
    (bs/nav-item {:key 2 :title "Item"} "NavItem 2 content")
    (bs/nav-item {:key 3 :disabled true} "NavItem 3 content"))
   (dom/hr)
   (mkdn "### Navbars")
   (bs/navbar
    (bs/nav
     (bs/nav-item {:key 1 :href "#!/bootstrap_cljs_devcards/ex-13"} "Link")
     (bs/nav-item {:key 2 :href "#!/bootstrap_cljs_devcards/ex-13"} "Link")
     (bs/nav-dropdown {:key 3 :title "Dropdown" :id "a-dropdown"}
                         (bs/menu-item {:key "1"} "Action")
                         (bs/menu-item {:key "2"} "Another action")
                         (bs/menu-item {:key "3"} "Something else here")
                         (bs/menu-item {:key "4" :divider true})
                         (bs/menu-item {:key "5"} "Separated link"))))
   (dom/hr)

   (mkdn "### Tabs")
   (bs/tabs {:default-active-key 2 :id "tabs-example"}
     (map (fn [num]
            (bs/tab {:event-key num :title (str "Tab " num)}
              (dom/div {:style {:padding "10px"}} (str "Content " num)))) (range 1 4)))
   (dom/hr)
   (mkdn "### Pagers")
   (bs/pager
    (bs/page-item {:href "#!/bootstrap_cljs.devcards/ex-12"} "Previous")
    (bs/page-item {:href "#!/bootstrap_cljs.devcards/ex-14"} "Next"))))

(defcard ex-14
  (react-card
   (mkdn
    "## Alerts"
    "### Styles")
   (dom/div
    (map #(bs/alert {:bs-style %} "Alert style: " %) (drop 2 styles)))))

(defcomponent closable-alert [data owner]
  (init-state [_]
              {:visible? false})
  (render-state [_ {:keys [visible?]}]
                (dom/div
                 (if visible?
                   (bs/alert
                    {:bs-style "danger"
                     :onDismiss #(om/set-state! owner :visible? false)}
                    (dom/h4 "This is an error.")
                    (dom/p "You can close it by pressing a button.")
                    (dom/br)
                    (bs/button-toolbar
                     (bs/button {:bs-style "danger"} "Lazy Button")
                     (bs/button {:on-click #(om/set-state! owner :visible? false)} "Hide Alert")))
                   (bs/button {:on-click #(om/set-state! owner :visible? true)} "Show Alert")))))

(defcard om-closable-alert
  (dc/om-root closable-alert)
  {:visible? true})

(defcard ex-15
  (react-card
   (mkdn "## Carousels")
   (bs/carousel
    (bs/carousel-item
     (dom/img {:width 900 :height 500 :src "img/carousel.png"})
     (dom/div {:class-name "carousel-caption"}
              (dom/h3 "First slide label")
              (dom/p "Nulla vitae elit libero, a pharetra augue mollis interdum.")))
    (bs/carousel-item
     (dom/img {:width 900 :height 500 :src "img/carousel.png"})
     (dom/div {:class-name "carousel-caption"}
              (dom/h3 "Second slide label")
              (dom/p "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")))
    (bs/carousel-item
     (dom/img {:width 900 :height 500 :src "img/carousel.png"})
     (dom/div {:class-name "carousel-caption"}
              (dom/h3 "Third slide label")
              (dom/p "Praesent commodo cursus magna, vel scelerisque nisl consectetur."))))))

(defcard ex-16
  (react-card
   (mkdn "## Labels")
   (dom/div
    (map #(dom/h4 (bs/label {:bs-style %} (str/capitalize %))) styles))))

(defcard forms-header (react-card (mkdn "# Forms")))

(defcard ex-17
  (react-card
    (mkdn "## Supported Controls")
    (dom/hr)
    (dom/div
      (dom/form
        (bs/form-group {:control-id "formControlsText"}
          (bs/control-label "Text")
          (bs/form-control {:type "text" :placeholder "enter text"}))

        (bs/form-group {:control-id "formControlsEmail"}
          (bs/control-label "Email Address")
          (bs/form-control {:type "email" :placeholder "enter email"}))

        (bs/form-group {:control-id "formControlsPassword"}
          (bs/control-label "Password")
          (bs/form-control {:type "password"}))

        (bs/form-group {:control-id "formControlsFile"}
          (bs/control-label "File")
          (bs/form-control {:type "file"})
          (bs/help-block "Example block-level help text here."))

        (bs/checkbox {:checked true :read-only true} "Checkbox")

        (bs/radio {:checked true :read-only true} "Radio")

        (bs/form-group
          (bs/checkbox {:inline true} 1)
          (bs/checkbox {:inline true} 2)
          (bs/checkbox {:inline true} 3))

        (bs/form-group
          (bs/radio {:inline true} 1)
          (bs/radio {:inline true} 2)
          (bs/radio {:inline true} 3))

        (bs/form-group {:control-id "formControlsSelect"}
          (bs/control-label "Select")
          (bs/form-control {:component-class "select" :placeholder "select"}
            (dom/option {:value "select"} "select")
            (dom/option {:value "other"} "...")))

        (bs/form-group {:control-id "formControlsSelectMultiple"}
          (bs/control-label "Multiple Select")
          (bs/form-control {:component-class "select" :multiple true}
            (dom/option {:value "select"} "select (multiple)")
            (dom/option {:value "other"} "...")))

        (bs/form-group {:control-id "formControlsTextarea"}
          (bs/control-label "Textarea")
          (bs/form-control {:component-class "textarea" :placeholder "textarea"}))

        (bs/form-group
          (bs/control-label "Static Text")
          (bs/form-control-static "email@example.com"))))))

(defcard ex-input-groups
  (react-card
    (mkdn "## Input groups")
    (dom/form
      (bs/form-group
        (bs/input-group
          (bs/input-group-addon "@")
          (bs/form-control {:type "text"})))

      (bs/form-group
        (bs/input-group
          (bs/form-control {:type "text"})
          (bs/input-group-addon ".00")))

      (bs/form-group
        (bs/input-group
          (bs/input-group-addon "@")
          (bs/form-control {:type "text"})
          (bs/input-group-addon ".00")))

      (bs/form-group
        (bs/input-group
          (bs/form-control {:type "text"})
          (bs/input-group-addon
            (bs/glyphicon {:glyph "music"}))))

      (bs/form-group
        (bs/input-group
          (bs/input-group-button
            (bs/button "Before"))
          (bs/form-control {:type "text"}))))))

(defcard validation-states
  (react-card
    (mkdn "## Validation states")
    (mkdn "Set `:validation-state` to one of 'success', 'warning' or
    'error'. Add `form-control-feedback` for a feedback icon.")

    (bs/form-group {:control-id "formValidationSuccess1" :validation-state "success"}
      (bs/control-label "Input with success")
      (bs/form-control {:type "text"})
      (bs/help-block "Help text with valiation state"))

    (bs/form-group {:control-id "formValidationSuccess1" :validation-state "warning"}
      (bs/control-label "Input with warning")
      (bs/form-control {:type "text"}))

    (bs/form-group {:control-id "formValidationSuccess1" :validation-state "error"}
      (bs/control-label "Input with error")
      (bs/form-control {:type "text"}))

    (bs/form-group {:control-id "formValidationSuccess1" :validation-state "success"}
      (bs/control-label "Input with success and feeback icon")
      (bs/form-control {:type "text"})
      (bs/form-control-feedback nil))))

(defcomponent modal [data owner]
  (init-state [_]
              {:visible? false})
  (render-state [_ {:keys [visible?]}]
                (dom/div
                 (mkdn "## Modals")
                 (bs/button {:on-click #(om/set-state! owner :visible? true)} "Show Modal")
                 (bs/modal {:title "Example modal"
                            :animated false
                            :show visible?
                            :on-hide #(om/set-state! owner :visible? false)}
                           (dom/div {:class "modal-body"}
                                    (mkdn "An example modal, with content."))
                           (dom/div {:class "modal-footer"}
                                    (bs/button {:on-click #(om/set-state! owner :visible? false)}
                                               "Close")
                                    (bs/button {:bs-style "primary"
                                                :on-click (fn [_]
                                                            (om/set-state! owner :visible? false)
                                                            (js/alert "Done!"))}
                                               "Done"))))))

(defcard om-modal
  (dc/om-root modal))

(defcard ex-18
  (react-card
   (mkdn
    "## Tables"
    (str "###  Set the `striped`, `bordered`, `condensed` or `hover`"
         " properties to true to control the behavior of the table"))
   (dom/br)
   (bs/table {:bordered true
              :striped true
              :hover true}
             (dom/thead
              (dom/tr
               (dom/td "Header 1")
               (dom/td "Header 2")
               (dom/td "Header 3")))
             (dom/tbody
              (dom/tr
               (dom/td "Cell 1-a")
               (dom/td "Cell 2-a")
               (dom/td "Cell 3-a"))
              (dom/tr
               (dom/td "Cell 1-b")
               (dom/td "Cell 2-b")
               (dom/td "Cell 3-b"))
              (dom/tr
               (dom/td "Cell 1-c")
               (dom/td "Cell 2-c")
               (dom/td "Cell 3-c"))
              (dom/tr
               (dom/td "Cell 1-d")
               (dom/td "Cell 2-d")
               (dom/td "Cell 3-d"))))))
