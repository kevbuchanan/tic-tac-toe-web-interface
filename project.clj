(defproject ttt-ring "0.0.1"
  :dependencies [[org.clojure/clojure "1.5.1"]
                [org.clojure/clojurescript "0.0-1859"
                    :exclusions [org.apache.ant/ant]]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [ring/ring-core "1.2.1"]
                 [tic-tac-toe "0.1.0-SNAPSHOT"]
                 [org.clojure/data.json "0.2.3"]
                 [jayq "2.5.0"]]
  :min-lein-version "2.0.0"
  :main ttt-ring.core
  :profiles {:dev {:dependencies [[speclj "2.5.0"]
                                  [specljs "2.8.0"]
                                  [ring/ring-devel "0.2.0"]]}}
  :plugins [[speclj "2.5.0"]
            [specljs "2.7.4"]
            [lein-ring "0.8.7"]
            [lein-cljsbuild "1.0.0-alpha1"]]
  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["spec/clj"]
  :cljsbuild ~(let [run-specs ["phantomjs" "bin/specljs_runner.js"  "resources/public/application2.js"]]
          {:builds {:dev {:source-paths ["src/cljs" "spec/cljs"]
                          :compiler {:output-to "resources/public/application2.js"
                                     :optimizations :whitespace
                                     :pretty-print true}
                          :notify-command run-specs}

                    :prod {:source-paths ["src/cljs"]
                           :compiler {:output-to "resources/public/application2.js"
                                      :optimizations :simple}}}

            :test-commands {"test" run-specs}})
  :ring {:handler ttt-ring.core/app})


