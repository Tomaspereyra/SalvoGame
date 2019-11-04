
function getPlayer(gameplayer){

return "<li class='list-group-item' style='width: 12em'><span class='badge'>" +
           gameplayer.player.email + "</span> </li>";}

function getGamePlayer(data){

return data.map(getPlayer).join("");}

function getItemHtml(game) {
var htmlData = getGamePlayer(game.gamePlayers);
  return "<li class='list-group-item' style='width: 12em'><span class='badge'>" +
    game.created + "</span><ol>"+htmlData+"</ol> </li>";
}


function getListHtml(data) {
        console.log(data);
  return data.map(getItemHtml).join("");
}
function renderList(data) {
  var html = getListHtml(data);
  document.getElementById("games").innerHTML = html;
}

function renderTable(data){
        var bodyTable = document.getElementById("body-table");
        var items =[];
    for(var i =0; i<data.length;i++){

        var item = "<tr><th scope='row'>"+data[i].player+"</th><td>"+ data[i].leaderBoard.totalScore+"</td><td>"+data[i].leaderBoard.totalWins+"</td><td>"+
        data[i].leaderBoard.totalLosses+"</td><td>"+data[i].leaderBoard.totalTies+"</td></tr>"
        items.push(item);
    }
        bodyTable.innerHTML =items.join("");

}
$.post("/app/login", { email: "j.bauer@ctu.gov", password: "24" }).done(function(){ console.log("vamoo");
$.get("http://localhost:8080/api/games").done(function(data){
renderList(data);
})

$.get("http://localhost:8080/api/leaderboard").done(function(data){
renderTable(data);
})

})




