(ns reagent-definition-table.core
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce app-state (atom {:text "Hello world!"}))


(defn app-root []
  [:div.ui.container
   [:h1.ui.dividing.header "Definition Table"]
   #_[:h3 "Edit this and watch it change!"]])

(reagent/render-component [app-root]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
