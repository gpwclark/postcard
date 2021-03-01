(ns postcard.html
	(:require [clojure.java.io :as io]
						[hiccup.page :as hic])
	(:gen-class))

(defn -front [img-link tagline]
	[:html
	 [:head [:style (slurp (io/resource "4x6-front.css"))]
					[:meta {:charset "UTF-8"}]
					[:link {:href "https://fonts.googleapis.com/css?family=Source+Sans+Pro:700"
												:rel "stylesheet"
												:type "text/css"}]
					[:title "clj generative art"]]
	 [:body {:style (str "background-image: url(" img-link ");")}
		[:div#safe-area
		 [:div#tagline tagline]]]])

(defn -back [img-link text]
	[:html
	 [:head [:style (slurp (io/resource "4x6-back.css"))]
		[:meta {:charset "UTF-8"}]
		[:link
		 {:href "https://fonts.googleapis.com/css?family=Roboto+Mono&display=swap",
						:rel "stylesheet"}]
		[:title "clj generative art"]]
	 [:body {:style (str "background-image: url(" img-link ");")}
		[:div#safe-area
		 [:div.text
			[:span text]]]]])

(defn write-file [filename out]
	(with-open [w (clojure.java.io/writer  filename :encoding "UTF-8")]
		(.write w out)))

(defn gen-back
	[{:keys [img-link text filename]}]
		(write-file filename (hic/html5 (-back img-link text))))

(defn gen-front
	[{:keys [img-link tagline filename]}]
		(write-file filename (hic/html5 (-front img-link tagline))))
