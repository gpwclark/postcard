(ns postcard.post
	(:import [com.lob Lob]
					 [com.lob.model Address$RequestBuilder]
					 [com.lob.model Postcard$RequestBuilder]
					 [com.lob.model Postcard])
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
