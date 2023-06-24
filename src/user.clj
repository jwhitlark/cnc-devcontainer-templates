(ns user
 (:require [clojure.string :as str]
           [clojure.set :as set]
           [clojure.java.shell :refer [sh]]
           [clojure.java.io :as io]
           [kubernetes-api.core :as k8s]))

(defn get-config []
  (:out (sh "k3d" "kubeconfig" "get" "k3s-default")))

(comment
  (def cf (get-config))
  (def api-server
    (-> (re-seq #"server: (.*)" cf) first second str/trim))
  (spit "ca.crt" (-> (re-seq #"certificate-authority-data: (.*)" cf) first second str/trim))
  (spit "client-cert.pem" (-> (re-seq #"client-certificate-data: (.*)" cf) first second str/trim))
  ;; This next one might need to be a java keystore
  (spit "client.key" (-> (re-seq #"client-key-data: (.*)" cf) first second str/trim))

  (def ca-path (.getAbsolutePath (io/file "ca.crt")))
  (def client-cert-path (.getAbsolutePath (io/file "client-cert.pem")))
  (def client-key-path (.getAbsolutePath (io/file "client.key")))

  (def k8s (k8s/client api-server {:ca-cert     ca-path
                                   :client-cert client-cert-path
                                   :client-key  client-key-path}))
  ;; end comment
  )