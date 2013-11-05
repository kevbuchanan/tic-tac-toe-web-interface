describe("Game", function(){

  var newGame
  var wonGame
  var drawGame

  var newBoard = ["-", "-", "-", "-", "-", "-", "-", "-", "-"]
  var wonBoard = ["X", "X", "X", "-", "-", "-", "O", "O", "-"]
  var drawBoard = ["X", "X", "O", "O", "O", "X", "X", "O", "X"]
  var bigBoard = new Array(50).join("-").split("")

  beforeEach(function(){
    newGame = new Game(newBoard, 3, "X", "playing", 3)
    wonGame = new Game(wonBoard, 3, "X", "win", 3, "X")
    drawGame = new Game(drawBoard, 3, "X", "draw", 3)
    bigGame = new Game(bigBoard, 7, "X", "playing", 3)
    loadFixtures('game-fixture.html')
  })

  it("should have a board", function(){
    expect(newGame.board).toEqual(newBoard)
  })

  it("should have a size", function(){
    expect(newGame.size).toEqual(3)
  })

  it("should have a piece", function(){
    expect(newGame.piece).toEqual("X")
  })

  it("should have a state", function(){
    expect(newGame.state).toEqual("playing")
  })

  it("should have a difficulty", function(){
    expect(newGame.difficulty).toEqual(3)
  })

  describe("#start", function(){
    it("displays the board", function(){
      spyOn(newGame, 'displayBoard')
      newGame.start()
      expect(newGame.displayBoard).toHaveBeenCalled()
    })

    describe("when the game is over", function(){
      it("ends the game", function(){
        spyOn(wonGame, 'end')
        wonGame.start()
        expect(wonGame.end).toHaveBeenCalled()
      })
    })

    describe("when the game is not over", function(){
      it("listens for the next move", function(){
        spyOn(newGame, 'listenForMove')
        newGame.start()
        expect(newGame.listenForMove).toHaveBeenCalled()
      })
    })
  })

  describe("#update", function(){
    it("updates the games attributes", function(){
      newGame.update({board: wonBoard, size: 3, piece: "X", state: "win", difficulty: 3, winner: "X"})
      expect(newGame.board).toEqual(wonBoard)
      expect(newGame.size).toEqual(3)
      expect(newGame.piece).toEqual("X")
      expect(newGame.state).toEqual("win")
      expect(newGame.difficulty).toEqual(3)
      expect(newGame.winner).toEqual("X")
    })

    it("calls #start", function(){
      spyOn(newGame, 'start')
      newGame.update({board: wonBoard, size: 3, piece: "X", state: "win", difficulty: 3, winner: "X"})
      expect(newGame.start).toHaveBeenCalled()
    })
  })

  describe("#end", function(){
    it("turns off the click listener", function(){
      wonGame.displayBoard()
      wonGame.listenForMove()
      wonGame.end()
      var square = $('li.square:nth-of-type(4)')
      square.click()
      expect($('img.load')).not.toExist()
    })

    describe("when the game is a draw", function(){
      it("displays the draw message", function(){
        drawGame.end()
        expect($('h3.draw')).toBeVisible()
      })
    })

    describe("when the game has a winner", function(){
      it("displays the win message", function(){
        wonGame.end()
        expect($('p.win')).toBeVisible()
      })
    })
  })

  describe("#displayBoard", function(){
    it("should display a 3x3 board", function(){
      newGame.displayBoard()
      expect($('li.square').length).toEqual(9)
    })

    it("should display a 7x7 board", function(){
      bigGame.displayBoard()
      expect($('li.square').length).toEqual(49)
    })

    it("should assign the empty class to empty squares", function(){
      wonGame.displayBoard()
      expect($('li.square.empty').length).toEqual(4)
    })

    it("should assign the correct spot id to the square's data attribute", function(){
      newGame.displayBoard()
      expect($('li.square:nth-of-type(2)').data('spot')).toEqual(1)
    })

    it("should assign the correct character to the square", function(){
      wonGame.displayBoard()
      expect($('li.square:nth-of-type(1)').html()).toEqual("X")
    })
  })

  describe("#listenForMove", function(){
    beforeEach(function(){
      wonGame.displayBoard()
      wonGame.listenForMove()
    })

    describe("on an empty space", function(){
      it("should trigger the click event", function(){
        var square = $('li.square:nth-of-type(4)')
        square.click()
        expect($('img.load')).toExist()
      })

      beforeEach(function(){
        var square = $('li.square:nth-of-type(4)')
        spyOn($, 'post')
        square.click()
        request = $.post.mostRecentCall
      })

      it("should send an ajax request to the correct route", function(){
        expect(request.args[0]).toEqual("/game")
      })

      it("should send an ajax request with the correct board", function(){
        expect(request.args[1].board).toEqual(wonBoard)
      })

      it("should send an ajax request with the correct move", function(){
        expect(request.args[1].move).toEqual(3)
      })

      it("should send an ajax request with the correct piece", function(){
        expect(request.args[1].piece).toEqual("X")
      })

      it("should send an ajax request with the correct difficulty", function(){
        expect(request.args[1].difficulty).toEqual(3)
      })
    })

    describe("on a taken space", function(){
      it("should not trigger the click event", function(){
        var square = $('li.square:nth-of-type(2)')
        square.click()
        expect($('img.load')).not.toExist()
      })
    })
  })
})
