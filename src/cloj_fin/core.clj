(ns cloj-fin.core
  (:import (org.jsoup Jsoup))
  (:require [clj-http.client :as client]))

; need to recieve some data from an api endpoint and once we recieve this data then when
; can start to calculate certain statistics off of it such as different types of moving
; averages. It would then be nice if we implement some kind of regression so that thematical
; data can then be used to make assumptions about the movements.
; it looks like we are going to have to use some java APIs to get the http requests working
; in the way that I want it to since I'm not sure how to write an HTTP client from scratch

; create the URL
(defn yahoo-fin-url [ticker tab]
  "creates a url endpoint for yahoo finanance given the ticker and tab"
  (let [tab-end (case tab
                  "stats" "/key-statistics"
                  "history" "/history"
                  "financials" "/financials"
                  "analysis" "/analysis"
                  "")]
    (format "https://finance.yahoo.com/quote/%s%s?p=%s" ticker tab-end ticker)))


; parse the different data tabs on yahoo finance
; we will have an individual function for each of the different tabs since
; they will all have different data structures to them
(defn yahoo-fin-parser-stats [html-string]
  "takes in an html-string and parses the data out as clojure data structure")


; pulls everything togther into a single function
(defn yahoo-fin-data [ticker tab]
  "creates the url, pulls the data, and parses the data from yahoo finance"
  (let [url (yahoo-fin-url "AAPL" "stats")]
    (let [parser (case tab
                   "stats" yahoo-fin-parser-stats)]
      (->> (client/get url)))))




; I am also going to want to collect information about the company from Yahoo finance or a
; similar website so that we can make more informed decisions on the different stocks
; - continuation trade setup
; - reversal trade setup
; - range-bound trade setup
; - breakout trade setup

; this might a another good website for https://tradingsim.com/blog/day-trading-setups/
; trying to automate looking for different setups


(defn -main []
  (let [url (yahoo-fin-url "AAPL" "summary")]
    (println url)))
