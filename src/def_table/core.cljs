(ns def-table.core
  (:require [cljs.spec.alpha :as s]))


(s/def ::path-to-value (s/coll-of keyword :kind vector))
(s/def ::label string?)
(s/def ::formatter fn?)
(s/def ::row-def (s/tuple ::path-to-value ::label ::formatter))
(s/def ::rows-def (s/coll-of ::row-def :min-count 1 :distinct true))


(defn definition-table [obj rows-def & [options]]
  {:pre [(if (s/valid? ::rows-def rows-def)
           true
           (js/console.error (s/explain-str ::rows-def rows-def)))]}

  [:table.ui.definition.table
   {:class (:table-css-class options)}
   (into [:tbody]
         (map (fn [[path-to-value label formatter]]
                [:tr
                 [:td
                  {:class (:left-column-css-class options)}
                  [:div
                   {:class (when (:ribbon-label? options)
                             "ui ribbon label")}
                   label]]
                 [:td
                  {:class (:right-column-css-class options)}
                  (formatter (get-in obj path-to-value))]])
              rows-def))])


