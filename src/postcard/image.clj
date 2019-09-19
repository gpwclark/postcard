(ns postcard.image
	(:require [quil.core :as q :include-macros true]
						[quil.middleware :as m])
	(:gen-class))

(def img-name "/tmp/circle.svg")

(defn mm-to-px
	[mm]
	(/ (* mm 72) 25.4))
(def a4-w (mm-to-px 210))
(def a4-h (mm-to-px 297))

(defn draw []
	(q/no-loop)
	; (q/camera 150 150 150 0 0 0 0 0 1)
	(q/ellipse 100 100 100 100)
	; (q/save img-name)
	; stop sketch after saving image
	; otherwise it will show save dialog
	; on every iteration
	; (q/exit)
	)

(defn sketch-svg
	""
	[]
	(q/sketch
	 :draw (fn []
					 (q/do-record (q/create-graphics a4-w a4-h :svg img-name)
												(draw)) ))
	)

(defn gen-image
	""
	[x]
	(sketch-svg)
	(println img-name))
