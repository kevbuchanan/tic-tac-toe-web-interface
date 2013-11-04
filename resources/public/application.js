var gameArea = "#game"
var startForm = "#start"
var boardId = "#board"
var boardStruct = "<ul id='board'></ul>"
var emptySpace = "-"
var emptySquare = "li.square.empty"

var square = function(piece, index){
  if (piece == emptySpace){
    return "<li class='square empty' data-spot='" + index + "'>" + piece + "</li>"
  }
  else {
    return "<li class='square' data-spot='" + index + "'>" + piece + "</li>"
  }
}

var winMessage = function(winner){
  return "<h3>Game Over</h3><p>" + winner + " won!</p>"
}

var drawMessage = "<h3>Draw</h3>"

function Game(board, size, playerPiece, state, difficulty) {
  this.board = board
  this.size = size
  this.piece = playerPiece
  this.move = null
  this.state = state
  this.difficulty = difficulty
  this.displayBoard = function(){
    $(gameArea).empty()
    $(gameArea).append(boardStruct)
    var size = this.size
    this.board.forEach(function(el, index){
      $(boardId).append(square(el, index))
      if ((index % size) == size - 1){
        $(boardId).append("<br>")
      }
    })
  }
  this.listenForMove = function(){
    var that = this
    $(emptySquare).on("click", function(event){
      var move = $(this).data("spot")
      that.move = move
      var url = "/game"
      var data = {"board" : that.board,
                  "piece" : that.piece,
                  "move" : that.move,
                  "difficulty" : that.difficulty}
      $.post(url, data, function(response){
        startGame(response)
      }, "JSON")
    })
  }
}

var startGame = function(gameData){
  var game = new Game(gameData.board, gameData.size, gameData.piece, gameData.state, gameData.difficulty)
  game.displayBoard()
  if (game.state == "playing"){
    game.listenForMove()
  }
  else {
    endGame(gameData.state, gameData.winner)
  }
}

var endGame = function(state, winner){
  $(emptySquare).off()
  if (state == "win"){
    $(gameArea).append(winMessage(winner))
  }
  else {
    $(gameArea).append(drawMessage)
  }
}

$(document).ready(function(){
  $(startForm).on("submit", function(event){
    event.preventDefault()
    var data = $(this).serialize()
    var url = $(this).attr("action")
    $.post(url, data, function(response){
      startGame(response)
    }, "JSON")
  })
})
