(ns postcard.core
	(:require [cli-matic.core :refer [run-cmd]]
						[postcard.post :as post]
						[postcard.image :as img]
						[postcard.pdf :as pdf]
						[postcard.html :as html])
	(:gen-class))

(def address-opts
	(list {:option "to" :short "t" :as "name of recipient" :type :string :default "Spike Spiegel"}
				{:option "line1" :short "1" :as "first line of recipient address" :type :string :default "100 two-hundredths street"}
				{:option "line2" :short "2" :as "second line of recipient address" :type :string :default "Apt B"}
				{:option "city" :short "c" :as "city of recipient address" :type :string :default "Springfield"}
				{:option "state" :short "s" :as "state of recipient address" :type :string :default "Iowa"}
				{:option "zip" :short "z" :as "zip code of recipient address" :type :string :default "52721"}))

(defn gen-card-description
	[type]
	(str "Generate the postcard body or 'letter' part in " type " format. This will be the written note on the back of the
	postcard
	. It
	will be half of the surface area."))

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
									:runs post/print-post}
								 {:command "list" :short "l"
									:description ["list postcards"
																""
																"Add optional limit, default 10."]
									:opts [{:option "limit" :as "To limit list by..." :type :string :default "10"}]
									:runs post/list-postcards}
								 {:command "image" :short "i"
									:description ["Generate an image to use as the front of the postcard."]
									:runs img/gen-image}
								 {:command "fonts" :short "f"
									:description ["list fonrts available for pdf format."]
									:runs pdf/list-fonts}
								 {:command "pdf" :short "p"
									:description [(gen-card-description "pdf")]
									:opts [{:option "content" :short "c" :as "Provide content for postcard." :type :string :default
																	"No matter where you go, there you are."}]
									:runs pdf/gen-body}
								 {:command "html" :short "h"
									:description [(gen-card-description "html")]
									:opts [{:option "content" :short "c" :as "Provide content for postcard." :type :string :default
																	"No matter where you go, there you are."}]
									:runs html/gen-body}
								 ]})

(defn -main
	"This is our entry point.
	pass parameters and configuration.
	functionality will be invoked, see --help."
	[& args]
	(run-cmd args CONFIGURATION))

