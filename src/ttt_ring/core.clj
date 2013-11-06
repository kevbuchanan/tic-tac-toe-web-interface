(ns ttt-ring.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.file :refer [wrap-file]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ttt-ring.routes :refer :all]))

(def routes {:get {"/" game-index
                   "/favicon.ico" favicon
                   "/application.js" (-> js (wrap-file "resources/public") (wrap-file-info))
                   "/style.css" (-> css (wrap-file "resources/public") (wrap-file-info))
                    "/ajax-loader.gif" (-> gif (wrap-file "resources/public") (wrap-file-info))}
             :post {"/" (wrap-params create-game)
                    "/game" (wrap-params update-game)}})

(defn app [req & handlers]
  (let [handlers (or (first handlers) routes)
        controller (or (get ((:request-method req) handlers) (:uri req)) not-found)]
    (controller req)))

(defn -main [& [port]]
  (jetty/run-jetty app {:port (Integer. (get (System/getenv) "PORT" "5000")) :join? false}))
