(ns cloj-fin.core
  (:import (org.jsoup Jsoup))
  (:require [clj-http.client :as client]))

; need to recieve some data from an api endpoint and once we recieve this data then when
; can start to calculate certain statistics off of it such as different types of moving
; averages. It would then be nice if we implement some kind of regression so that thematical
; data can then be used to make assumptions about the movements.
; it looks like we are going to have to use some java APIs to get the http requests working
; in the way that I want it to since I'm not sure how to write an HTTP client from scratch

(defn yahoo-fin-url [ticker tab]
  "creates a url endpoint for yahoo finanance given the ticker and tab"
  (let [tab-end (case tab
                  "stats" "/key-statistics"
                  "history" "/history"
                  "financials" "/financials"
                  "analysis" "/analysis"
                  "")]
    (format "https://finance.yahoo.com/quote/%s%s?p=%s" ticker tab-end ticker)))


; I want to end up doing more error handling here, but I'm not sure the best way
; to have these errors be handled.
(defn get-url [url]
  "gets data from a specific url and handles any errors within the response"
  (let [resp (client/get url)]
    (resp)))

; this should give me the full request that I want to yahoo finance for the historical data
(def h {"User-Agent" "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:94.0) Gecko/20100101 Firefox/94.0"
        "Accept" "text/html"
        "Accept-Language" "en-US,en;q=0.5"})

(def qp {"period1" "1606618744"
         "period2" "1638154744"
         "interval" "1d"})

(client/get "https://finance.yahoo.com/quote/AAPL/history" 
            {:headers h :query-params qp})


; parse the different data tabs on yahoo finance
; we will have an individual function for each of the different tabs since
; they will all have different data structures to them
(defn yahoo-fin-parser-summary [html]
  "takes in an html string from the yahoo finance summary tab and parses out the data"
  (let [soup (Jsoup/parse html)
        data (as-> soup s
               (.getElementsByTag s "table")
               (take 2 s)
               (for [table s, 
                     row (.getElementsByTag table "tr"), 
                     data (.getElementsByTag row "td")] 
                 (.text data))
               (apply hash-map s))]
    {:data data}))

(defn yahoo-fin-parser-stats [html]
  "takes in an html string from the yahoo finance stats tab and parses out the data"
  (let [soup (Jsoup/parse html)
        data (as-> soup s)]
    {:data data}))


; pulls everything togther into a single function
(defn yahoo-fin-data [ticker tab]
  "creates the url, pulls the data, and parses the data from yahoo finance"
  (let [url (yahoo-fin-url ticker tab)
        html (get (client/get url) :body)
        parser (case tab
                 "stats" yahoo-fin-parser-stats
                 yahoo-fin-parser-summary)]
    (parser html)))


; I am also going to want to collect information about the company from Yahoo finance or a
; similar website so that we can make more informed decisions on the different stocks
; - continuation trade setup
; - reversal trade setup
; - range-bound trade setup
; - breakout trade setup

; this might a another good website for https://tradingsim.com/blog/day-trading-setups/
; trying to automate looking for different setups


(defn -main []
  (let [data (yahoo-fin-data "AAPL" "summary")]
    (println (-> data (get :data))))
  (let [data (yahoo-fin-data "AAPL" "stats")]
    (println (-> data (get :data))))
  )

