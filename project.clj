(defproject bootstrap-cljs "0.0.1"
  :description "ClojureScript wrapper around React Bootstrap"
  :url "https://github.com/luxbock/bootstrap-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jar-exclusions [#"\.cljx\.swp|\.swo|\.DS_Store"]
  :source-paths ["target/generated/src/clj" "src"]
  :resource-paths ["target/generated/src/cljs"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [prismatic/om-tools "0.2.2"]
                 [org.clojure/clojurescript "0.0-2268"]]
  :scm {:name "git"
        :url "https://github.com/luxbock/bootstrap-cljs"}
  :deploy-repositories [["clojars" {:creds :gpg}]]
  :profiles
  {:dev {:dependencies [[devcards "0.1.1-SNAPSHOT"]
                        [weasel "0.3.0"]
                        [om "0.6.4"]]
         :plugins [[org.clojure/clojurescript "0.0-2268"]
                   [com.keminglabs/cljx "0.4.0"]
                   [lein-cljsbuild "1.0.3"]
                   [lein-figwheel "0.1.3-SNAPSHOT"]]
         :source-paths ["src" "devcards_src"]}}
  :cljx {:builds [{:source-paths ["src"]
                   :output-path  "target/generated/src/clj"
                   :rules        :clj}
                  {:source-paths ["src"]
                   :output-path  "target/generated/src/cljs"
                   :rules        :cljs}]}
  :prep-tasks ["cljx" "javac" "compile"]

  :cljsbuild {:builds [{:source-paths ["target/generated/src/cljs"]
                        :compiler {:output-to     "target/main.js"
                                   :optimizations :whitespace
                                   :pretty-print  true}}
                       {:id "devcards"
                        :source-paths ["target/generated/src/cljs" "devcards_src"]
                        :compiler {:output-to     "resources/public/js/devcards.js"
                                   :output-dir    "resources/public/js/out"
                                   :source-map    "resources/public/js/devcards.js.map"
                                   :optimizations :none}}]})
