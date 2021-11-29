(ns yahoo-fin.history
  "functionality for pulling data from the history tab of yahoo finance"
  (:import (org.jsoup Jsoup))
  (:require [clj-http.client :as client]))


(defn req-url 
  "returns the request url given a valid url ticker value"
  [ticker] 
  (format "https://finance.yahoo.com/quote/%s/history" ticker))


(defn req-body
  "returns the request headers and parameters"
  [] 
  {:headers {"User-Agent" "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:94.0) Gecko/20100101 Firefox/94.0"
             "Accept" "text/html"
             "Accept-Language" "en-US,en;q=0.5"
             "Pragma" "no-cache"
             "Connection" "keep-alive"
             "Cache-Control" "no-cache"}
   ; for the time range we are only looking a max of 5 years back since anything 
   ; further is probably not needed for our given use case
   :query-params {"period1"(quot (- (System/currentTimeMillis) (* 31556952000 5)) 1000) 
                  "period2" (quot (System/currentTimeMillis) 1000)
                  "interval" "1d"} 
   ; the below two lines are needed since we don't care about cookies with
   ; this specific implementation
   :decode-cookies false 
   :cookie-policy :none})


(defn req-html
  "requests html given a valid ticker symbol and handle when something goes wrong"
  [ticker]
  (-> (client/get (req-url "AAPL") (req-body))))


(defn html->data
  [html]
  (println "we need to parse out the data"))


; MAIN FUNCTION
(defn -main []
  (let [resp (req-html "AAPL")]
    (println (get resp :status))))
