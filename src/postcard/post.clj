(ns postcard.post
  (:require [clojure.pprint :as pp])
	(:import [com.lob Lob]
					 [com.lob.model Address$RequestBuilder]
					 [com.lob.model Postcard$RequestBuilder]
					 [com.lob.model Postcard])
	(:gen-class))

(def lob-version "2020-02-11")

(defn get-address
	"given an address, return address obj"
	[name line1 line2 city state zip]
	(cond-> (Address$RequestBuilder.)
			name (.setName name)
			line1 (.setLine1 line1)
			line2 (.setLine2 line2)
			city (.setCity city)
			state (.setState state)
			zip (.setZip zip)))

(defn get-postcard
	""
	[^Address$RequestBuilder from ^Address$RequestBuilder to front back]
	(-> (Postcard$RequestBuilder.)
			(.setDescription "clj generative art")
			(.setTo to)
			(.setFrom from)
			(.setFront (slurp front))
			(.setBack (slurp back))
			(.create)
			(.getResponseBody)))

(defn post
	"Post postcard via http to lob."
	[{:keys [to line1 line2 city state zip front back key]}]
	(let [init-lob (Lob/init key lob-version)
				to (get-address to line1 line2 city state zip)
				sender (System/getenv "FROM")
				from (get-address (System/getenv "FROM")
													(System/getenv "LINE1")
													(System/getenv "LINE2")
													(System/getenv "CITY")
													(System/getenv "STATE")
													(System/getenv "ZIP"))
				postcard (get-postcard from to front back)]
			(println postcard)))


(defn list-postcards
	[{:keys [limit key]}]
	(let [x (+ 1 1)
				init (Lob/init key lob-version)]
		(pp/pprint (.getResponseBody (Postcard/list)))))
