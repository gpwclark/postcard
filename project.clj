(defproject postcard "1.0.0-SNAPSHOT"
	:description "Send postcaards with generated art."
	:plugins [[io.taylorwood/lein-native-image "0.3.1"]]
	:license {:name "Eclipse Public License"
						:url "http://www.eclipse.org/legal/epl-v10.html"}
	:dependencies [[org.clojure/clojure "1.9.0"]
								 [cli-matic "0.3.7"]
								 [com.lob/lob-java "9.0.5"]
								 [quil "3.0.0"]]
	:jvm-opts ["-Dclojure.compiler.direct-linking=true"]
	:main postcard.core
	:aot :all
	:native-image {:name "postcard.bin"
								 :opts ["--report-unsupported-elements-at-runtime"
												"--initialize-at-build-time"
												"--no-fallback"
												"-J-Xmx3G"
												"-J-Xms3G"
												;;"--allow-incomplete-classpath"
												]}
	)