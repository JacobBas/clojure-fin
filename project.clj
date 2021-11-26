(defproject cloj-fin "0.1.0-SNAPSHOT"
  :description "clojure project used for financial markets and stock analysis"
  :url "https://github.com/JacobBas/clojure-fin"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-http/clj-http "3.12.3"]
                 [org.jsoup/jsoup "1.13.1"]]
  :repl-options {:init-ns cloj-fin.core})
