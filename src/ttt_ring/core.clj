(ns ttt-ring.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ttt-ring.routes :refer :all]
            [tic-tac-toe.board :refer [new-board]]))

(defn app [req]
  (println req)
  (let [controller (get ((:request-method req) routes) (:uri req))]
    (controller req)))

(defn -main [& [port]]
  (jetty/run-jetty app {:port (Integer. (or port 5000)) :join? false}))
