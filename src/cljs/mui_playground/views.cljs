(ns mui-playground.views
    (:require [re-frame.core :as rf]
              [reagent-material-ui.core :as m]))

(def paper-style {
                  :height 700
                  :width "80%"
                  :margin 30
                  :textAlign "center"
                  :display "inline-block"
                  })

(defn ui []
  [m/Paper {:style paper-style :zDepth 4}

   [m/Tabs
    [m/Tab {:label "Purchase"}]
    [m/Tab {:label "Refinance"}]
    [m/Tab {:label "Prequalification"}]
    ]

   [m/DatePicker {:hintText "Birthday"}]

   [m/RaisedButton {:backgroundColor "red" :label "I like this loan" :labelColor "white"}]

   [m/Slider {:defaultValue 0.3}]

   ]
  )

(defn main-panel []
  (let [name (rf/subscribe [:name])]
    (fn []
      [ui])))

