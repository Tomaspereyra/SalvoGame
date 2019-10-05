
function getPlayer(gameplayer){

return "<li class='list-group-item' style='width: 12em'><span class='badge'>" +
           gameplayer.player.email + "</span> </li>";}

function getGamePlayer(data){

return data.map(getPlayer).join("");}

function getItemHtml(game) {
var htmlData = getGamePlayer(game.gamePlayers);
console.log(htmlData);
  return "<li class='list-group-item' style='width: 12em'><span class='badge'>" +
    game.created + "</span><ol>"+htmlData+"</ol> </li>";
}
function getListHtml(data) {
  return data.map(getItemHtml).join("");
}
function renderList(data) {
  var html = getListHtml(data);
  document.getElementById("games").innerHTML = html;
}

$.get("http://localhost:8080/api/games").done(function(data){
renderList(data);
})

