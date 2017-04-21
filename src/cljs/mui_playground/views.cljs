(ns mui-playground.views
    (:require [clojure.string :as str]
              [reagent-material-ui.core :as m]
              [reagent.core :as r]
              [re-frame.core :as rf]))

(defn- ev-val [ev]
  (-> ev .-target .-value))

(defn- format-number [n]
  (->> (str n)
       (reverse)
       (partition-all 3)
       (interpose ",")
       (flatten)
       (reverse)
       (apply str)))

(defn- unformat-number [s]
  (when-let [[_ m] (re-matches #"\$([\d,]*)" s)]
    (let [n (str/replace m #"\," "")]
      (when (pos? (count n))
        (js/parseInt n)))))

(def paper-style {
                  :width 700
                  :margin "30px auto"
                  :padding "25px 50px 75px 50px"
                  :display "block"
                  })

(defn credit-score-dd [& {:keys [model on-change label]}]
  (let [val (or (if (satisfies? IDeref model) @model model) 0)]
    [m/SelectField {:floatingLabelText label :value val :on-change (fn [_ _ v] (on-change v))}
     [m/MenuItem {:value "" :primaryText ""}]
     [m/MenuItem {:value "excellent" :label "Excellent" :primaryText "Excellent"}]
     [m/MenuItem {:value "vg" :label "Very good" :primaryText "Very good"}]
     ]))

(defn money-text-field [& {:keys [model on-change label id]}]
  (let [val (or (if (satisfies? IDeref model) @model model) 0)]
    [m/TextField {:id id
                  :floatingLabelText label
                  :value (str "$" (format-number val))
                  :onChange #(on-change (or (unformat-number (ev-val %)) 0))}]))

(defn ui []
  (let [model (r/atom {})] ; this will come from a subscription
    (fn []
      (let [{:keys [purchase-price down-payment credit-score]} @model]
        [m/MuiThemeProvider {:muiTheme (m/getMuiTheme #js {:palette #js {}})}
         [m/Paper {:style paper-style}
          [:h1.text-center "Get a real approval in just minutes!"]

          [:div.row
           [:div.col-md-6
            [:div.form-group
             [money-text-field :id "PurchasePrice"
              :model purchase-price
              :on-change #(swap! model assoc
                                 :purchase-price %)
              :label "Purchase price"]
             ]]
           [:div.col-md-6
            [:div.form-group
             [money-text-field :id "DownPayment"
              :model down-payment
              :on-change #(swap! model assoc
                                 :down-payment %)
              :label "Down payment"]
             ]]]

          [:div.row
           [:div.col-md-6
            [:div.form-group
             [m/SelectField {:floatingLabelText "Property state" :value "" :onChange (fn [_ _ v] ())}
              [m/MenuItem {:value "" }]
              [m/MenuItem {:value "AL" :label "Alabama" :primaryText "Alabama"}]
              [m/MenuItem {:value "IL" :label "Illinios" :primaryText "Illinios"}]
              ]
             ]]
           [:div.col-md-6
            [:div.form-group
             [credit-score-dd
              :model credit-score
              :label "Credit score"
              :on-change #(swap! model assoc
                                 :credit-score %)]
             ]]]

          [:div.row
           [:div.col-md-6
            [:div.form-group
             [m/TextField {:floatingLabelText "First Name"}]]]
           [:div.col-md-6
            [:div.form-group
             [m/TextField {:floatingLabelText "Last Name"}]]]
           ]

          [:div.row
           [:div.col-md-6
            [:div.form-group
             [m/TextField {:floatingLabelText "Email"}]]]
           [:div.col-md-6
            [:div.form-group
             [m/TextField {:floatingLabelText "Phone"}]]]
           ]

          [:div.row
           [:div.col-md-12
            [:div.form-group
             [:label.control-label "Are you working with a loan expert?"]
             [m/RadioButtonGroup {:name "lo" :defaultSelected "yes"}
              [m/RadioButton {:value "yes" :label "Yes" :style {:display "inline-block" :width "auto" :margin-right 25 }}]
              [m/RadioButton {:value "no" :label "No" :style {:display "inline-block", :width "auto"}}]
              ]
             ]]]

          [:div.row
           [:div.col-md-12.text-right
            [m/RaisedButton {:label "Next" :backgroundColor (.-red500 m/colors) :labelColor (.-white m/colors)}]]
           ]
          ]
         ])))
  )

(defn main-panel []
  (let [name (rf/subscribe [:name])]
    (fn []
      [ui])))

