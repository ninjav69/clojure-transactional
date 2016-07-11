(ns clojure-transactional.datomic-api-test
  (:require [clojure.test :refer :all]
            [datomic.api :as d]))

; Create the connect string for an in-memory database

(def db-uri "datomic:mem://hello")

; Create the in-memory database for the connect string

(d/create-database db-uri)

; Connect to the created database

(def conn (d/connect db-uri))

; Add a fact to the system

(def datom [:db/add (d/tempid :db.part/user) :db/doc "hello world"])
@(d/transact conn [datom])

(deftest test-find
  (testing "Can query for existing data"
    (def db (d/db conn))
    (is (= 1 (count (d/q '[:find ?e :where [?e :db/doc "hello world"]] db))))))
