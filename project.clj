(defproject bootstrap-cljs "0.0.6-SNAPSHOT"
  :description "ClojureScript wrapper around React Bootstrap"
  :url "https://github.com/luxbock/bootstrap-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jar-exclusions [#"\.cljx\.swp|\.swo|\.DS_Store"]
  :source-paths ["target/generated/src/clj" "src"]
  :resource-paths ["target/generated/src/cljs" "resources"]
  :dependencies [[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/clojurescript "1.7.228" :scope "provided"]
                 [cljsjs/react-bootstrap "0.28.1-0"]
                 [prismatic/om-tools "0.4.0"]]
  :scm {:name "git"
        :url "https://github.com/luxbock/bootstrap-cljs"}
  :deploy-repositories [["clojars" {:creds :gpg}]]
  :profiles
  {:dev {:dependencies [[devcards "0.2.1-5"]
                        [org.omcljs/om "0.9.0"]
                        [com.cemerick/piggieback "0.2.2-SNAPSHOT"]
                        [org.slf4j/slf4j-nop "1.7.13" :scope "test"]
                        [org.clojure/tools.nrepl "0.2.10"]]
         :plugins [[lein-cljsbuild "1.1.1"]
                   [deraen/lein-less4j "0.5.0"]
                   [lein-figwheel "0.5.0-1"]]
         :less {:target-path "resources/public/css/"
                :source-paths ["less_src"]
                :source-map true}
         :source-paths ["src" "devcards_src"]}}
  :prep-tasks ["javac" "compile"]
  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:output-to     "target/main.js"
                                   :optimizations :whitespace
                                   :pretty-print  true}}
                       {:id "devcards"
                        :source-paths ["src" "devcards_src"]
                        :figwheel { :devcards true }
                        :compiler {:main "bootstrap-cljs.devcards"
                                   :asset-path    "js/out"
                                   :output-to     "resources/public/js/devcards.js"
                                   :output-dir    "resources/public/js/out"
                                   :source-map    true
                                   :optimizations :none}}]})
