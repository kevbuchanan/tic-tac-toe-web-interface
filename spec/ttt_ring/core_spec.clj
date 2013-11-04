(ns ttt-ring.core-spec
  (:require [speclj.core :refer :all]
            [ttt-ring.core :refer :all]))

(describe "Routing a request"

  (def test-handlers {:get {"/test-get" (fn [x] "get")} :post {"/test-post" (fn [x] "post")}})

  (it "routes a get request to the correct controller if one exists"
    (should= "get" (app {:request-method :get :uri "/test-get"} test-handlers)))

  (it "routes a post request to the correct controller if one exists"
    (should= "post" (app {:request-method :post :uri "/test-post"} test-handlers)))

  (it "routes to the not found handler if the route does not exist"
    (should= {:status 404
              :headers {"Content-Type" "text/html"}
              :body "Page Not Found"} (app {:request-method :post :uri "/bad-request"} test-handlers))))
