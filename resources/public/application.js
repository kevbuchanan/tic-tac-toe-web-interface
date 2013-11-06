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
  return "<h3>Game Over</h3><p class='win'>" + winner + " won!</p>"
}

var drawMessage = "<h3 class='draw'>Draw</h3>"

function Game(board, size, playerPiece, state, difficulty, winner) {
  this.board = board
  this.size = size
  this.piece = playerPiece
  this.move = null
  this.state = state
  this.difficulty = difficulty
  this.winner = winner
}

Game.prototype.displayBoard = function(){
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

Game.prototype.listenForMove = function(){
  var that = this
  $(emptySquare).on("click", function(event){
    $(gameArea).append("<img class='load' src='/ajax-loader.gif'>")
    var move = $(this).data("spot")
    that.move = move
    var url = "/game"
    var data = {"board" : that.board,
                "piece" : that.piece,
                "move" : that.move,
                "difficulty" : that.difficulty}
    $.post(url, data, function(response){
      that.update(response)
    }, "JSON")
  })
}

Game.prototype.start = function(){
  this.displayBoard()
  if (this.state == "playing"){
    this.listenForMove()
  }
  else {
    this.end()
  }
}

Game.prototype.update = function(gameData){
  this.board = gameData.board
  this.size = gameData.size
  this.piece = gameData.piece
  this.move = null
  this.state = gameData.state
  this.difficulty = gameData.difficulty
  this.winner = gameData.winner
  this.start()
}

Game.prototype.end = function(){
  $(emptySquare).off()
  if (this.state == "win"){
    $(gameArea).append(winMessage(this.winner))
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
      game = new Game(response.board, response.size, response.piece, response.state, response.difficulty, response.winner)
      game.start()
    }, "JSON")
  })
})
