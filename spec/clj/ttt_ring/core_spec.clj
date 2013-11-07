(ns ttt-ring.core-spec
  (:require [speclj.core :refer :all]
            [ttt-ring.core :refer :all]))

(describe "Routing a request"

  (it "routes a request to the correct controller if one exists"
    (should= 200 (:status (handler {:request-method :get :uri "/"}))))

  (it "routes to the not found handler if the route does not exist"
    (should= 404 (:status (handler {:request-method :post :uri "/bad-request"})))))
