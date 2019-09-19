(ns postcard.core
	(:import [com.lob Lob]
					 [com.lob.model Address$RequestBuilder]
					 [com.lob.model Postcard$RequestBuilder]
					 [com.lob.model Postcard])
	(:require [cli-matic.core :refer [run-cmd]]
						[postcard.image :as img]
						[postcard.pdf :as body])
	(:gen-class))

(defn get-address
	"given an address, return address obj"
	[name line1 line2 city state zip]
	(-> (Address$RequestBuilder.)
			(.setName name)
			(.setLine1 line1)
			(.setLine2 line2)
			(.setCity city)
			(.setState state)
			(.setZip zip)))

(defn get-postcard
	""
	[from address]
	(-> (Postcard$RequestBuilder.)
			(.setDescription "demo clojure postcard")
			(.setTo address)
			(.setFrom from)
			(.setFront "https://lob.com/postcardfront.pdf")
			(.setBack "https://lob.com/postcardback.pdf")
			))

(defn post
	"Post postcard via http to lob."
	[{:keys [to line1 line2 city state zip key]}]
	(let [address (get-address to line1 line2 city state zip)
				from (get-address (System/getenv "SENDER")
													(System/getenv "LINE1")
													(System/getenv "LINE2")
													(System/getenv "CITY")
													(System/getenv "STATE")
													(System/getenv "ZIP"))
				postcard (get-postcard from address)]
		(do
			(Lob/init key "2019-06-01")
			(-> (.create postcard)
					(.getResponseBody)))))

(defn print-post
	[a]
	(println (post a)))

(defn list-postcards
	[{:keys [limit key]}]
	(let [x (+ 1 1)
				init (Lob/init key "2019-06-01")]
		(println (.getResponseBody (Postcard/list)))))

(def address-opts
	(list {:option "to" :short "t" :as "name of recipient" :type :string :default "Spike Spiegel"}
				{:option "line1" :short "1" :as "first line of recipient address" :type :string :default "100 two-hundredths street"}
				{:option "line2" :short "2" :as "second line of recipient address" :type :string :default "Apt B"}
				{:option "city" :short "c" :as "city of recipient address" :type :string :default "Springfield"}
				{:option "state" :short "s" :as "state of recipient address" :type :string :default "Iowa"}
				{:option "zip" :short "z" :as "zip code of recipient address" :type :string :default "52721"}))

(def CONFIGURATION
	{:app
								{:command "postcard"
								 :description "Command line app to post postcard to lob."
								 :version "0.1"}
	 :global-opts [{:option "key" :as "Key to use w/ api" :type :string :default "09SA9NL!Z82F93111LJ"}]
	 :commands
								[{:command "post" :short "p"
									:description ["Post to lob"
																""
																"Looks great, doesn't it?"]
									:opts address-opts
									:runs print-post}
								 {:command "list" :short "l"
									:description ["list postcards"
																""
																"Add optional limit, default 10."]
									:opts [{:option "limit" :as "To limit list by..." :type :string :default "10"}]
									:runs list-postcards}
								 {:command "image" :short "i"
									:description ["Generate an image to use as the front of the postcard."]
									:runs img/gen-image}
								 {:command "fonts" :short "f"
									:description ["Generate the postcard body or 'letter' part. This will be the written note on the
									back of the postcard. It will be half of the surface area."]
									:runs body/list-fonts}
								 {:command "body" :short "b"
									:description ["Generate the postcard body or 'letter' part. This will be the written note on the
									back of the postcard. It will be half of the surface area."]
									:opts [{:option "content" :short "c" :as "Provide content for postcard." :type :string :default
																	"No matter where you go, there you are."}]
									:runs body/gen-body}
								 ]})

(defn -main
	"This is our entry point.
	pass parameters and configuration.
	functionality will be invoked, see --help."
	[& args]
	(run-cmd args CONFIGURATION))

