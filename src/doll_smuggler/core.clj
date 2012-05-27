(ns doll-smuggler.core)

(require 'clojure.string)

; let's first try to reinvent a wheel :)
; here goes custom solution which works on given sample of data
; but probably will fail on more complicated samples.

; reads data from a file and returns a sequence of maps containing :name, :value, :cost and maximum capacity restriction
(defn parse-file-data [filename]
  (let
    [
      input (clojure.string/split-lines (slurp filename))
      capacity (read-string (first input))
      data (map (fn [x] (let
        [
          vals (clojure.string/split x #"\s+")
          name (first vals)
          cost (read-string (second vals))
          value (read-string (last vals))
        ]
        { :name name, :cost cost, :value value }
      )) (next input))

    ]
    [data, capacity]
  )
)

; removes every first doll in a sequence until the capacity restriction is met
(defn squeeze [squeezed_dolls capacity]
  (if
    (> (apply + (map (comp :cost) squeezed_dolls)) capacity)
    (squeeze (drop 1 squeezed_dolls) capacity)
    squeezed_dolls
  )
)

; rearranges dolls in a sequence taking into account doll's value per doll's weight (:factor)
; and then calls squeeze
(defn reinvented-wheel [dolls capacity]
  (let [
      dolls (map #(merge %1 {:factor (/ (get %1 :cost) (get %1 :value))}) dolls) ; add :factor to every doll record
      dolls (reverse (sort-by :factor dolls))
    ]
    (squeeze dolls capacity) ;do some magic
  )
)


; and let's also use classic solution
; http://rosettacode.org/wiki/Knapsack_problem/0-1#Clojure

(declare mm)
(defn m [i w dolls]
  (cond
    (< i 0) [0 []]
    (= w 0) [0 []]
    :else
    (let [{wi :cost vi :value} (get dolls i)]
      (if (> wi w)
        (mm (dec i) w dolls)
        (let [[vn sn :as no]  (mm (dec i) w dolls)
              [vy sy :as yes] (mm (dec i) (- w wi) dolls)]
          (if (> (+ vy vi) vn)
            [(+ vy vi) (conj sy i)]
            no))))))

(def mm (memoize m))
(defn dynamic-approach[dolls capacity]
  (let [
      dolls (vec dolls)
      [value indexes] (m (-> dolls count dec) capacity dolls)
      names (map (comp :name dolls) indexes)
    ]
    [names value (reduce + (map (comp :cost dolls) indexes))]
  )
)