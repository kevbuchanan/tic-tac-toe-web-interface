(ns ttt-ring.applicationjs
  (:require [jayq.core :as jq]))

(defn add-form-listener []
  (jq/on (jq/$ "#start") :submit
    (fn [event]
      (jq/append (jq/$ "body") "<div id='loading'></div>"))))
