(ns ttt-ring.adapter-spec
  (:require [speclj.core :refer :all]
            [ttt-ring.adapter :refer :all])
  (:import [kevin.server Request]
           [java.io ByteArrayInputStream]))

(describe "Adapter"

  (defn test-handler [request] true)

  (def test-request
    (Request. (ByteArrayInputStream. (.getBytes "GET / HTTP/1.1\r\nContent-Length: 9\r\n\r\ndata=test"))))

  (it "creates an instance of Callable"
    (should (proxy-handler test-handler)))

  (it "creates a server"
    (should (create-server (proxy-handler test-handler) 5000)))

  (describe "build-request"
    (def request-map (build-request-map test-request))

    (it "parses the method"
      (should= :get (:request-method request-map)))

    (it "parses the uri"
      (should= "/" (:uri request-map)))

    (it "parses the body"
      (should= "data=test" (slurp (:body request-map) :encoding "UTF-8")))

    (it "parses the headers"
      (should-not= nil (:headers request-map))))

  (describe "build-response-map"
    (def response-map (build-response-map {:status 200 :headers {"Content-Type" "text/html"} :body "Test"}))

    (it "creates a status"
      (should= 200 (.status response-map)))

    (it "creates a body"
      (should= "Test" (.text (.body response-map))))))


