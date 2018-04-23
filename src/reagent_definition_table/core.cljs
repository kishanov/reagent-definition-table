(ns reagent-definition-table.core
  (:require [reagent.core :as reagent :refer [atom]]
            [def-table.core :as dt]
            [cljs.pprint :as pp]))


(def device-payload
  {:id    "00e93c16-467a-11e8-842f-0ed5f89f718b"
   :facts {:hw_model        "NX-OSv"
           :hw_version      "0.0"
           :mgmt_ifname     "mgmt0"
           :mgmt_ipaddr     "172.20.194.22"
           :mgmt_macaddr    "52:54:00:23:42:DC"
           :os_arch         "x86_64"
           :os_family       "NXOS"
           :os_version      "7.0(3)I7(1)"
           :os_version_info {:build "(3)I7(1)"
                             :major 7
                             :minor 0}
           :serial_number   "5254002342DC"
           :vendor          "Cisco"}})


(def provider-payload
  {:provider_name    "test1"
   :hostname_fqdn_ip ["10.1.3.126"
                      "10.1.3.122"]
   :port             49
   :shared_key       "testing123"
   :auth_mode        "ASCII"
   :active           true
   :vendor           "TACACS+"
   :check_login      {:username "tacacs-admin"
                      :password "admin"}})


(defn as-list [item-formatter coll]
  (->> coll
       (map (fn [e] [:div.item (item-formatter e)]))
       (into [:div.ui.items])))


(defn as-code [value]
  [:pre
   [:code value]])


(defn as-label [css-classes value]
  [:div.ui.label
   {:class css-classes}
   value])


(defn inspect [data & [pprint?]]
  [:pre
   [:code
    (if pprint?
      (with-out-str (pp/pprint data))
      (.stringify js/JSON (clj->js data) nil 2))]])


(defn app-root []
  [:div.ui.container
   [:div.ui.internally.celled.grid
    [:div.one.column.row
     [:div.column
      [:h2 "Definition Table"]]]

    [:div.two.column.row
     [:div.column
      [dt/definition-table
       provider-payload
       (list [[:provider_name] "Name" identity]
             [[:port] "Port" identity]
             [[:vendor] "Vendor" (partial as-label "big default")]
             [[:auth_mode] "Auth Mode" (partial as-label "teal")]
             [[:active] "Active?" (fn [v] (if v "yes" "no"))]
             [[:hostname_fqdn_ip] "Hostname FQDNs" (partial as-list as-code)])
       {:ribbon-label?          true
        :left-column-css-class  "four wide"
        :right-column-css-class "twelve wide"}]]

     [:div.column
      [inspect provider-payload]]]

    [:div.one.column.row
     [:div.column
      [:h2 "Definition Sections"]]]

    [:div.two.column.row
     [:div.column
      [dt/definition-sections
       device-payload
       (list ["HW Info"
              (list [[:facts :vendor] "Vendor" identity]
                    [[:facts :serial_number] "S/N" identity]
                    [[:facts :hw_model] "Model" identity]
                    [[:facts :hw_version] "Version" identity])]

             ["Connectivity"
              (list [[:facts :mgmt_ifname] "Interface Name" identity]
                    [[:facts :mgmt_ipaddr] "IP Addrress" identity]
                    [[:facts :mgmt_macaddr] "Mac Address" identity])]

             ["OS Info"
              (list [[:facts :os_arch] "Architecture" identity]
                    [[:facts :os_family] "Family" identity]
                    [[:facts :os_version] "Version" identity])])]]

     [:div.column
      [inspect device-payload]]]]])

(reagent/render-component [app-root]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
