(ns om-bootstrap-devcards.examples
  (:require [devcards.core :as dc :include-macros true]
            [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-bootstrap.dom :as bs])
  (:require-macros [devcards.core :refer [defcard is are= are-not=]]
                   [om-bootstrap-devcards.examples :refer [defntraced sourced-ex]]))

(enable-console-print!)

(devcards.core/start-devcard-ui!)
(devcards.core/start-figwheel-reloader!)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/index.html

(defn mkdn [mkd-strs]
  (dc/react-raw (dc/less-sensitive-markdown [mkd-strs])))

(defcard part-1
  (dc/react-card
    (dom/div
      (mkdn
        "## Buttons
        ### Options
        Use any of the available style types to quicjly create a styled button.
        Just modify the `bsStyle` prop.")
      (dom/br "")
      (sourced-ex
        (bs/button-toolbar
          (bs/button "Default")
          (bs/button {:bsStyle "primary"} "Primary")
          (bs/button {:bsStyle "success"} "Success")
          (bs/button {:bsStyle "info"} "Info")
          (bs/button {:bsStyle "warning"} "Warning")
          (bs/button {:bsStyle "danger"} "Danger")))
      (dom/hr "")
      (mkdn
        "### Sizes
         Fancy larger or smaller buttons? Add `:bsSize \"large\"`,
         `:bsSize \"small\"`, or `:bsSize \"xsmall\"` for additional sizes.")
      (dom/br "")
      (sourced-ex
        (dom/div
          (bs/button-toolbar
            (bs/button {:bsStyle "primary" :bsSize "large"} "Large button")
            (bs/button {:bsSize "large"} "Large button"))
          (dom/br "")
          (bs/button-toolbar
            (bs/button {:bsStyle "primary"} "Default button")
            (bs/button "Default button"))
          (dom/br "")
          (bs/button-toolbar
            (bs/button {:bsStyle "primary" :bsSize "small"} "Small button")
            (bs/button {:bsSize "small"} "Small button"))
          (dom/br "")
          (bs/button-toolbar
            (bs/button {:bsStyle "primary" :bsSize "xsmall"} "Extra small button")
            (bs/button {:bsSize "xsmall"} "Extra small button"))))
      (dom/hr "")
      (mkdn
        "Create block level buttons - those that span the full width of a parent
         -- by adding the `:block true` prop.")
      (sourced-ex
        (let [well-styles {:maxWidth 400, :margin "0 auto 10px"}]
          (dom/div {:class "well" :style well-styles}
            (bs/button {:bsStyle "primary" :bsSize "large" :block true}
              "Block level button")
            (bs/button {:bsSize "large" :block true} "Block level button"))))
      (dom/hr "")
      (mkdn
        "### Active state
        To set a buttons active state simply set the components `:active` 
        prop to `true`.")
      (sourced-ex
        (bs/button-toolbar
          (bs/button {:bsStyle "primary" :bsSize "large" :active true}
            "Primary button")
          (bs/button {:bsSize "large" :active true} "Button")))
      (dom/hr "")
      (mkdn
        "### Disabled state 
        Make buttons look unclickable by fading them back 50%. To do this add
        the `:disabled true` attribute to buttons")
      (sourced-ex
        (bs/button-toolbar
          (bs/button {:bsStyle "primary" :bsSize "large" :disabled true}
            "Primary button")
          (bs/button {:bsSize "large" :disabled true} "Button")))
      (dom/hr "")
      (mkdn
        "### Button tags 
        The DOM element tag is choosen automaticly for you
        based on the props you supply. Passing a `href` will result in the
        button using a `<a />` element otherwise a `button` element will be
        used.")
      (sourced-ex
        (bs/button-toolbar
          (bs/button {:href "#"} "Link")
          (bs/button "Button")))
      (dom/hr ""))
    {:heading false}))

(defn toggle-loading [owner]
  (om/set-state! owner :loading?
    (not (om/get-state owner :loading?))))

(defcomponent loading-button [data owner]
  (init-state [_] {:loading? false})
  (render-state [_ {:keys [loading?]}]
    (bs/button {:bsStyle "primary"
                :disabled loading?
                :onClick
                (if loading?
                  nil
                  (fn []
                    (toggle-loading owner)
                    (js/setTimeout #(toggle-loading owner) 2000)))}
      (if loading? "Loading..." "Loading state"))))

(defcard om-test
  (dc/om-root-card
    (fn [owner data]
      (om/component
        (dom/div
          (mkdn
            "### Button loading state
          When activating an asynchronous action from
          a button it is a good UX pattern to give the user feedback as to the
          loading state, this can easily be done by updating your `button`’s
          props from a state change like below."))))
    {}))

(defcard loading-button
  (dc/om-root-card

    (om/component
      (dom/div
        (mkdn
          "### Button loading state
          When activating an asynchronous action from a button it is a good UX
          pattern to give the user feedback as to the loading state, this can
          easily be done by updating your `button`’s props from a state change
          like below.")))
    {}))

(defcard part-2
  (dc/react-card
    (dom/div
      (mkdn
        "## Button groups
        Group a series of buttons together on a single line with the button group.
        
        ### Basic example
        Wrap a series of `button`'s in a `button-group`.")
      (sourced-ex
        (bs/button-group
          (bs/button "Left")
          (bs/button "Middle")
          (bs/button "Right")))
      (dom/hr "")
      (mkdn
        "### Button toolbar
        Combine sets of `button-group`'s insto a `button-toolbar` for more 
        complex components.")
      (sourced-ex
        (bs/button-toolbar
          (apply bs/button-group
            (map #(bs/button (str %)) (range 1 5)))
          (apply bs/button-group
            (map #(bs/button (str %)) (range 5 8)))
          (bs/button-group
            (bs/button "8"))))
      (dom/hr "")
      (mkdn
        "### Sizing
         Instead of applying button sizing props to every button in a group,
         just add `:bsSize` prop to the `button-group`.")
      (sourced-ex
        (dom/div
          (map #(bs/button-toolbar
                  (bs/button-group {:bsSize %}
                    (bs/button "Left")
                    (bs/button "Middle")
                    (bs/button "Right")))
            ["large" "medium" "small" "xsmall"])))
      (dom/hr "")
      (mkdn
        "### Nesting
        You can place other button types within the `button-group` like 
        `dropdown-button`'s.")
      (sourced-ex
        (bs/button-group
          (bs/button "1")
          (bs/button "2")
          (bs/dropdown-button {:title "Dropdown"}
            (bs/menu-item {:key "1"} "Dropdown link")
            (bs/menu-item {:key "2"} "Dropdown link"))))
      (dom/hr "")
      (mkdn
        "### Vertical variation
        Make set of buttons appear vertically stacked rather than horizontally.
        *Split button dropdowns are not supported here.*

        Just add `:vertical true` to the `button-group`.")
      (sourced-ex
        (bs/button-group {:vertical true}
          (bs/button "Button")
          (bs/button "Button")
          (bs/dropdown-button {:title "Dropdown"}
            (bs/menu-item {:key "1"} "Dropdown link")
            (bs/menu-item {:key "2"} "Dropdown link"))
          (bs/button "Button")
          (bs/button "Button")
          (bs/dropdown-button {:title "Dropdown"}
            (bs/menu-item {:key "1"} "Dropdown link")
            (bs/menu-item {:key "2"} "Dropdown link"))
          (bs/dropdown-button {:title "Dropdown"}
            (bs/menu-item {:key "1"} "Dropdown link")
            (bs/menu-item {:key "2"} "Dropdown link"))))
      (dom/hr "")
      (mkdn
        "### Justified button groups
        Make a group of buttons a stretch at equal sizes to span the entire
        width of its parent. Also works with button dropdowns within the button
        group.

        Just add `:justified true` to the `button-group`.")
      (sourced-ex
        (bs/button-group {:justified true}
          (bs/button {:href "#"} "Left")
          (bs/button {:href "#"} "Middle")
          (bs/dropdown-button {:title "Dropdown"}
            (bs/menu-item {:key "1"} "Dropdown link")
            (bs/menu-item {:key "2"} "Dropdown link"))))
      (dom/hr "")
      
      )
    {:heading false}))
