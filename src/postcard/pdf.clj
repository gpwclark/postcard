(ns postcard.pdf
	(:require [quil.core :as q :include-macros true]
						[clojure.string :as str]
		[quil.middleware :as m])
	(:gen-class))

(def body-pdf "/tmp/circle.pdf")

(def height-in 4.25)
(def width-in 6.25)
(def dpi 300)
(df height-pix (* height-in dpi))
(df width-pix (* width-in dpi))
(def writable-area (/ width-pix 3))

(defn get-width
	""
	[x]
	(q/text-width x))

(defn halve
	""
	[text]
	(loop [accrual "" lst (str/split text #"\s+")]
		(let [item (first lst)
					rst (rest lst)
					p (println "my item " (type item))
					test-accrual (str accrual " " item)
					width (get-width test-accrual)
					new-accrual (if (> width writable-area)
												(str accrual "\n" item)
												test-accrual)]
		(if (empty? lst)
				accrual
				(recur new-accrual rst)))))

(defn draw [content]
	(let [get-text (fn [] (q/text (halve content) 10 10))]
	 (q/no-loop)
	 (q/text-mode :shape)
	 (q/fill 0 0 0)
	 (q/text-font (q/create-font "Monospaced.plain" 8))
	 (q/text-align :left :top)
	 (get-text)))

(defn sketch-body
	""
	[content]
	(q/sketch
		:draw (fn []
						(q/do-record (q/create-graphics width-pix height-pix :pdf body-pdf)
												 (draw content)))))

(defn gen-body
	""
	[{:keys [content]}]
	(sketch-body content)
	(println body-pdf))

;; TODO this is not right re-find for what?
(defn fonts []
	(let [xs (q/available-fonts)]
		(filter #(re-find #"(?i)" %) xs)))

(defn list-fonts
	""
	[x]
	(run! println (fonts)))
