(ns doll-smuggler.test.core
  (:use [doll-smuggler.core])
  (:use [clojure.test])
)

(let [[dolls capacity] (parse-file-data "data/data.txt")]
  (def my-result (reinvented-wheel dolls capacity))
  (def dynamic-approach-result (dynamic-approach dolls capacity))
)

(deftest my-result-test
  (is
    (=
      (sort ["sally" "eddie" "grumpy" "dusty" "grumpkin" "marc" "randal" "puppy" "dorothy" "candice" "anthony" "luke"])
      (sort (map (comp :name) my-result))
    )
    "wrong set of dolls"
  )

  (is
    (=
      (apply + (map (comp :cost) my-result))
      396
    )
    "wrong total weight for granny"
  )

  (is
    (=
      (apply + (map (comp :value) my-result))
      1030
    )
    "wrong total value for all dolls being carried"
  )

)


(deftest dynamic-approach-test
  (let [[names value weight] dynamic-approach-result]
    (is
      (=
        (sort ["sally" "eddie" "grumpy" "dusty" "grumpkin" "marc" "randal" "puppy" "dorothy" "candice" "anthony" "luke"])
        (sort names)
      )
      "wrong set of dolls"
    )

    (is
      (=
        weight
        396
      )
      "wrong total weight for granny"
    )

    (is
      (=
        value
        1030
      )
      "wrong total value for all dolls being carried"
    )
  )
)
