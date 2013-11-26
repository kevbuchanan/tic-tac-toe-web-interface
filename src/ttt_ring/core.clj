(ns ttt-ring.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.file :refer [wrap-file]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ttt-ring.routes :refer :all]
            [ttt-ring.adapter :refer [run-kevins-server]]))

(def routes {:get {"/" game-index}
             :post {"/" create-game
                    "/game" update-game}})

(defn handler [request]
  (let [controller (or (get ((:request-method request) routes) (:uri request)) not-found)]
    (controller request)))

(def app (-> handler
             wrap-params
             (wrap-file "resources/public")
             wrap-file-info))

(defn -main [& [port]]
  (run-kevins-server app {:port (Integer. (get (System/getenv) "PORT" "5000")) :join? false}))
