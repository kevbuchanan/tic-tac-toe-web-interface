(ns ttt-ring.applicationjs-spec
  (:require-macros [specljs.core :refer [describe it should-not= should=]])
  (:require [specljs.core]
            [ttt-ring.applicationjs :as app]
            [jayq.core :as jq]))

(def test-form "<form id='start'><input type='radio' name='size' value='1' checked></form>")

(describe "Starting a game"
  (it "listens for a #start form submission"
    (jq/append (jq/$ "body") test-form)
    (should-not= nil (jq/$ "#start"))
    (app/add-form-listener)
    (jq/trigger (jq/$ "#start") :submit)
    (should-not= nil (jq/$ "#loading"))))
