(ns postcard.html
	(:require [clojure.java.io :as io]
						[clojure.edn :as edn])
	(:import (java.io PushbackReader))
	(:gen-class))

(def data-file (io/resource "4x6-back.edn"))

(defn write-file [filename out]
	(with-open [w (clojure.java.io/writer  filename :encoding "UTF-8")]
		(.write w out)))

(defn gen-body
	"Given a map with a background image :img and a body :body
	generate html for a postcard at filename :filename."
	[{:keys [img body filename]}]
	(let [fill (fn [m]
							 (cond
									(:img m) img
									(:body m) body
									:else ""))
				test (fn [x y] (run! println y))
				accum (fn [txt next]
								(if (map? next)
									(str txt (fill next))
									(str txt next)))]
		(try
			(with-open [r (io/reader data-file)]
				(->> (PushbackReader. r)
						 (edn/read)
						 (reduce accum)
						 (write-file filename))))))
