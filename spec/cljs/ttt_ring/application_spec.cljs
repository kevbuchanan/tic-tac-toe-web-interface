(ns ttt-ring.applicationjs-spec
  (:require-macros [specljs.core :refer [describe it should-not= should=]])
  (:require [specljs.core]
            [ttt-ring.applicationjs :as app]
            [dommy.core :as dom]
            [dommy.utils :as utils])
  (:use-macros [dommy.macros :only [sel sel1 node]]))

(def test-form (node [:form#start "Start form"]))

(describe "Starting a game"
  (it "listens for a #start form submission"
    (dom/append! (sel1 :body) test-form)
    (should-not= nil (sel1 "#start"))
    (app/add-form-listener)
    (dom/fire! (sel1 "#start") :submit)
    (should-not= nil (sel1 "#loading"))))
