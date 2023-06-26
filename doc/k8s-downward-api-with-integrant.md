# K8s, podinfo, downward-api, clojure, & integrant


# Overview

# Kubernetes setup

## Enable the downward api in the k8s manifest as a volume

``` yaml
volumes:
  - name: podinfo
    downwardAPI:
      items:
        - path: "labels"
          fieldRef:
            fieldPath: metadata.labels
        - path: "annotations"
          fieldRef:
            fieldPath: metadata.annotations
```
## Mount the volume in the pod

This is expected to be at `spec.containers` in a `Pod`.  For a `Deployment`, it would be at `spec.template.spec.containers`.

``` yaml
volumeMounts:
- name: podinfo
  mountPath: /etc/podinfo
```

### Where and how it shows up

This will create or shadow the folder `/etc/podinfo` inside your container.

Note that as the labels and annotations are updated in Kubernetes, these files will be updated in the standard atomic unix fashion of writing to a different file then atomically moving it to replace the existing file.  This has implications for watching the files, as you will want to filter out any file changes except for `labels` and `annotations` (in this example, but you're free to call them whatever you want).  I believe both `labels` and `annotations` are updated whenever **either** is changed, so be aware that you might get a change notice without the contents actually changing.

### Making it work with local development

I usually just check for the existence of the `/etc/podinfo` directory, and if it doesn't load it from a releative path.

# Best practices for labels and annotations

Standardize your namespace, labels, and annotations.  Decide on standards for things like environment, instrumentation (profilier, etc.)

Use exisiting labels where possible:

    labels:
      app.kubernetes.io/name: mysql
      app.kubernetes.io/instance: mysql-abcxzy
      app.kubernetes.io/version: "5.7.21"

Note that `app.kubernetes.io` is reservered, as are other namespaces.

## If you really want to be nice to ops, consider adding some of the following annotations

These are not just useful for dev and ops, but can be used in the app itself to direct administrators and even everyday users to the appropriate location.

| Annotation | Description |
| ----------- | ----------- |
| a8r.io/description | Unstructured text description of the service for humans. |
| a8r.io/owner | GitHub or equivalent username (prefix with @), email address, or unstructured owner description. |
| a8r.io/chat | Slack channel (prefix with #), or link to other external chat system. |
| a8r.io/bugs | Link to external bug tracker. |
| a8r.io/logs | Link to external log viewer. |
| a8r.io/documentation | Link to external project documentation. |
| a8r.io/repository | Link to external VCS repository. |
| a8r.io/support | Link to external support center. |
| a8r.io/runbook | Link to external project runbook. |
| a8r.io/incidents | Link to external incident dashboard. |
| a8r.io/uptime | Link to external uptime dashboard. |
| a8r.io/performance | Link to external performance dashboard. |
| a8r.io/dependencies | Unstructured text description of the service dependencies for humans. |

# Integrant code to watch the directory, and when it changes, update an atom

## Dependencies

    {:deps {integrant/integrant {:mvn/version "0.8.1"}
            juxt/dirwatch {:mvn/version "0.2.5"}}}

## Code

``` clojure
(ns cnc.podinfo
  (:require [integrant.core :as ig]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [juxt.dirwatch :refer [watch-dir close-watcher]]))

(def config {:cnc/podinfo {:labels (atom {}),
                          :annotations (atom {})
                          :watcher nil}})

(defn load-props
  "The files produced by the downward api are close enough to property
  files that we'll use the built in property reader to parse them."
  [file-handle]
  (with-open [^java.io.Reader reader (io/reader file-handle)]
    (let [props (java.util.Properties.)]
      (.load props reader)
      (->> (for [[k v] props]
            [(keyword k) (edn/read-string v)])
          (into {} )))))

(defn downward-api-watcher
  "When a file event comes in, reload the atom. Note that we don't use
  the given file handle, as it will point to the temporary file."
  [labels, annotations, {:keys [file, count, action]}]
  (let [fname (.getName file)]
    (case fname
      "labels"      (let [props (load-props (io/file "/etc/podinfo/labels"))] (reset! labels props))
      "annotations" (let [props (load-props (io/file "/etc/podinfo/annotations"))] (reset! annotations props)))))

(defmethod ig/init-key :cnc/podinfo [_ {:keys [labels annotations watcher]}]
  (let [watcher (watch-dir (partial downward-api-watcher labels annotations)
        (io/file "/etc/podinfo/"))]
        {:labels labels :annotations annotations :watcher watcher}))

(defmethod ig/halt-key! :cnc/podinfo [_ {:keys [watcher]}]
  (close-watcher watcher))
```

## Using it in your components

### If they will check it each time

deref the atom

### If they need to watch it and take action when it changes

set up a watch

# References

* [Labels & Selectors](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/)
* [Common Labels](https://kubernetes.io/docs/concepts/overview/working-with-objects/common-labels/)
* [Exhaustive list of Well-Known Labels, Annotations, and Taints](https://kubernetes.io/docs/reference/labels-annotations-taints/)

* [Kubernetes Annotations](https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations/)
* [Annotations for Humans](https://ambassadorlabs.github.io/k8s-for-humans/)

* [The Kubernetes DownwardAPI](https://kubernetes.io/docs/concepts/workloads/pods/downward-api/)
* [Exposing certain data as environmental variables](https://kubernetes.io/docs/tasks/inject-data-application/environment-variable-expose-pod-information/)
* [Exposing labels and annotations as (updatable) files](https://kubernetes.io/docs/tasks/inject-data-application/downward-api-volume-expose-pod-information/)