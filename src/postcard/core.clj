(ns postcard.core
	(:require [cli-matic.core :refer [run-cmd]]
						[postcard.post :as post]
						[postcard.html :as html])
	(:gen-class))

(def address-opts
	(list {:option "to" :short "t" :as "name of recipient" :type :string :default "Spike Spiegel"}
				{:option "line1" :short "1" :as "first line of recipient address" :type :string :default "100 two-hundredths street"}
				{:option "line2" :short "2" :as "second line of recipient address" :type :string :default "Apt B"}
				{:option "city" :short "c" :as "city of recipient address" :type :string :default "Springfield"}
				{:option "state" :short "s" :as "state of recipient address" :type :string :default "Iowa"}
				{:option "zip" :short "z" :as "zip code of recipient address" :type :string :default "52721"}))

(def CONFIGURATION
	{:app {:command "postcard"
				 :description "Command line app to send postcards with lob."
				 :version "0.1"}
	 :global-opts [{:option "key" :as "Key to use w/ api" :type :string :default "09SA9NL!Z82F93111LJ"}]
	 :commands
	 [{:command "post" :short "p"
		 :description ["Post to lob"
									 ""
									 "Looks great, doesn't it?"]
		 :opts address-opts
		 :runs post/print-post}

		{:command "list" :short "l"
		 :description ["list postcards"
									 ""
									 "Add optional limit, default 10."]
		 :opts [{:option "limit" :as "To limit list by..." :type :string :default "10"}]
		 :runs post/list-postcards}
		{:command "front" :short "f"
		 :description ["Create postcard front... where the big image is."]
		 :opts [{:option "tagline"
						 :short "t" :as "Provide tagline for front of postcard."
						 :type :string :default "I computed art and sent this postcard from the terminal with clojure!"}
						{:option "img-link"
						 :short "i" :as "Provide valid 4.25\" x 6.25\" image link."
						 :type :string :default "https://priceclark.dev/postcard/cool-image.png"}
						{:option "filename"
						 :short "f" :as "Provide filename to write html file."
						 :type :string :default "front.html"}]
		 :runs html/gen-front}
		{:command "back" :short "b"
		 :description ["Create postcard front... where the big image is."]
		 :opts [{:option "text"
						 :short "t" :as "Provide text for back of postcard. Write something nice!"
						 :type :string :default "No matter where you go, there you are."}
						{:option "img-link"
						 :short "i" :as "Provide valid 4.25\" x 6.25\" image link."
						 :type :string :default "https://priceclark.dev/postcard/cool-image-back.png"}
						{:option "filename"
						 :short "f" :as "Provide filename to write html file."
						 :type :string :default "back.html"}]
		 :runs html/gen-back}
		]})


(defn -main
	"This is our entry point.
	pass parameters and configuration.
	functionality will be invoked, see --help."
	[& args]
	(run-cmd args CONFIGURATION))

