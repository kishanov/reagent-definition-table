(ns def-table.core)


(defn definition-table [obj rows-def & [options]]
  [:table.ui.definition.table
   (into [:tbody]
         (map (fn [[path-to-value label formatter]]
                [:tr
                 [:td label]
                 [:td (formatter (get-in obj path-to-value))]])
              rows-def))])


