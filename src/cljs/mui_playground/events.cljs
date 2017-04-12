(ns mui-playground.events
    (:require [re-frame.core :as re-frame]
              [mui-playground.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
