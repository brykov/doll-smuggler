I decided to try to impement something that comes to my mind first.

Thus, I came up with an idea of an algorithm that involves specific ranking for dolls.
I.e. every doll is given a factor that represents specific value per unit of weight.
After the factors are calculated, gramma's handbag is filled with dolls having greatest factors.
Given data-set sample is solved properly by this algorithm. Though it's obvious that it's not optimal
and will fail on larger sets.

In order to address this issue I've also adopted classic dynamic programming solution found on the internets.

Please note: this is my first experience with Clojure language (and it caused me a lot of pain:)