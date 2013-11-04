(ns ttt-ring.routes-spec
  (:require [speclj.core :refer :all]
            [ttt-ring.routes :refer :all]
            [clojure.data.json :as json]))

(describe "Creating a new game"

  (def new-game-request {:form-params {"size" 3 "difficulty" 3 "order" 2}})

  (it "sets the correct board size"
    (should= 9 (count (get (json/read-str (:body (create-game new-game-request))) "board"))))

  (it "sets the correct order"
    (should= ["-" "-" "-" "-" "O" "-" "-" "-" "-"] (get (json/read-str (:body (create-game new-game-request))) "board")))

  (it "sets the correct difficulty"
    (should= 3 (get (json/read-str (:body (create-game new-game-request))) "difficulty"))))

(describe "Updating a game"

  (def new-game-move-request {:form-params {"move" 0 "board[]" ["-" "-" "-" "-" "-" "-" "-" "-" "-"] "piece" "X" "difficulty" 3}})

  (def draw-game-move-request {:form-params {"move" 0 "board[]" ["-" "X" "O" "O" "O" "X" "X" "O" "X"] "piece" "X" "difficulty" 3}})

  (def win-game-move-request {:form-params {"move" 0 "board[]" ["-" "X" "X" "O" "O" "X" "O" "X" "O"] "piece" "X" "difficulty" 3}})

  (it "it makes the provided move on the board"
    (should= ["X" "-" "-" "-" "O" "-" "-" "-" "-"] (get (json/read-str (:body (update-game new-game-move-request))) "board")))

  (it "asks for the next move if the game is not over"
    (should= "playing" (get (json/read-str (:body (update-game new-game-move-request))) "state")))

  (it "shows a draw if the game is a draw"
    (should= "draw" (get (json/read-str (:body (update-game draw-game-move-request))) "state")))

  (it "shows the winner if the game is won"
    (should= "win" (get (json/read-str (:body (update-game win-game-move-request))) "state"))))
