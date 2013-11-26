(ns ttt-ring.adapter
  "Adapter for kevin's server."
  (:import [kevin.server.Callable]
           [kevin.server Server Request Response TextBody FileBody]
           [java.io File ByteArrayInputStream]))

(defn build-response-map [{:keys [status headers body]}]
  (if (= (type body) File)
    (doto (Response. status)
      (.addBody (FileBody. (.toString body))))
    (doto (Response. status)
      (.addBody (TextBody. body)))))

(defn get-headers [^Request request] {})

(defn build-request-map [^Request request]
  {:request-method (keyword (.toLowerCase (.method request)))
   :uri (.route request)
   :body (ByteArrayInputStream. (.getBytes (.body request)))
   :headers (get-headers request)
   :content-type (.get (.headers request) "Content-Type")})

(defn proxy-handler [handler]
  (proxy [kevin.server.Callable] []
    (call [^Request request]
      (let [request-map (build-request-map request)
            response (handler request-map)]
        (build-response-map response)))))

(defn create-server [handler port]
  (let [server (Server. ^Integer port ^kevin.server.Callable handler)]
    server))

(defn run-kevins-server [handler options]
  (doto (create-server (proxy-handler handler) (:port options))
    (.start)))
