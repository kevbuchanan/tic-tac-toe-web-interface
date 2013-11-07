(ns ttt-ring.round-spec
  (:require [speclj.core :refer :all]
            [ttt-ring.round :refer :all]))

(describe "starting a round"

    (defn move-test1 [player board piece difficulty] 1)

    (defn end-test [board] board)

    (it "ends if the game is over"
      (should= [:X :- :X :O :O :O :- :- :-] (start {:players [:ai :human]
                             :pieces [:X :O]
                             :board [:X :- :X :O :O :O :- :- :-]
                             :difficulty 3
                             :move-fn move-test1
                             :end-fn end-test})))

    (it "gets a move from the ai if the game is not over"
      (should= [:X :X :X :O :O :- :- :- :-] (start {:players [:ai :human]
                             :pieces [:X :O]
                             :board [:X :- :X :O :O :- :- :- :-]
                             :difficulty 3
                             :move-fn move-test1
                             :end-fn end-test})))

    (defn move-test2 [player board piece difficulty] (if (= player :ai) 8 5))

    (it "gets a human move from the move function if it's the human's turn"
      (should= 5 (start {:players [:human :ai]
                             :pieces [:X :O]
                             :board [:X :- :X :O :O :- :- :- :-]
                             :difficulty 3
                             :move-fn move-test2
                             :end-fn end-test}))))
