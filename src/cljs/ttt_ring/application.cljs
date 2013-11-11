(ns ttt-ring.applicationjs
  (:require [dommy.core :as dom]
            [dommy.utils :as utils])
  (:use-macros [dommy.macros :only [sel sel1 node]]))

(defn add-form-listener []
  (dom/listen! (sel1 "#start") :submit
    (fn [event]
     (.preventDefault event)
     (js/alert (.-currentTarget event))
      (dom/append! (sel1 "body") (node [:div#loading "loading"])))))

(set! (.-onload js/window) add-form-listener)
