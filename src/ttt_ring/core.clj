(ns ttt-ring.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]
            [ttt-ring.routes :refer :all]))

(def routes {:get {"/" game-index
                   "/favicon.ico" favicon
                   "/application.js" js
                   "/style.css" css
                    "/loader-gif" gif}
             :post {"/" (wrap-params create-game)
                    "/game" (wrap-params update-game)}})

(defn app [req & handlers]
  (let [handlers (or (first handlers) routes)
        controller (or (get ((:request-method req) handlers) (:uri req)) not-found)]
    (controller req)))

(defn -main [& [port]]
  (jetty/run-jetty app {:port (Integer. (get (System/getenv) "PORT" "5000")) :join? false}))
