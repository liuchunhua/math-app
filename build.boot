(set-env!
 :source-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 ])

(task-options!
 pom {:project 'math-app/math-app
      :version "0.2.0"}
 aot {:namespace #{'math-app.core 'math-app.math}}
 repl {:init-ns 'math-app.core})

(deftask package
  "Builds an uberjar of this project that can be run with java -jar"
  []
  (comp
   (aot)
   (uber)
   (jar)
   (sift)
   (target)))
