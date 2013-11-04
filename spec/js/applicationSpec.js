describe("Game", function(){
  var newGame
  var wonGame
  var drawGame

  var newBoard = ["-", "-", "-", "-", "-", "-", "-", "-", "-"]
  var wonBoard = ["X", "X", "X", "-", "-", "-", "O", "O", "-"]
  var drawBoard = ["X", "X", "O", "O", "O", "X", "X", "O", "X"]

  beforeEach(function(){
    newGame = new Game(newBoard, 3, "X", "playing", 3)
    wonGame = new Game(wonBoard, 3, "X", "won", 3)
    drawGame = new Game(drawBoard, 3, "X", "draw", 3)
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
})

