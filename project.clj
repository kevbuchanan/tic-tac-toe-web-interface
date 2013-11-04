(defproject ttt-ring "0.0.1"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [ring/ring-core "1.2.1"]
                 [tic-tac-toe "0.1.0-SNAPSHOT"]
                 [org.clojure/data.json "0.2.3"]]
  :min-lein-version "2.0.0"
  :main ttt-ring.core
  :profiles {:dev {:dependencies [[speclj "2.5.0"] [ring/ring-devel "0.2.0"]]}}
  :plugins [[speclj "2.5.0"]
            [lein-ring "0.8.7"]]
  :test-paths ["spec/"]
  :ring {:handler ttt-ring.core/app})

