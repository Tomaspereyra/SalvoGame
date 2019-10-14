$(function() {

    // parametros de URL (Uniform Resource Location).
    const urlParams = new URLSearchParams(window.location.search);

    // encabezados de la grilla
    let numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
    let letters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];

    // Posiciones de todos los barcos del jugador.
    let locations = [];

    //Setea los salvo de el jugador
    let locationsSalvo = []
    //tiros del oponente
    oponentShots=[]

    // genera el HTML de los encabezados de la grilla.
    function getHeadersHtml(headers) {
        return "<tr><th></th>" + headers.map(function(header) {
            return "<th>" + header + "</th>";
        }).join("") + "</tr>";
    }

    // Dibuja los encabezados.
    function renderHeaders() {
        var html = getHeadersHtml(numbers);
        document.getElementById("grid-numbers").innerHTML = html;
        document.getElementById("grid-salvo-numbers").innerHTML = html;
    }

    // Genera el HTML de las columnas.
    function getColumnsHtml(i) {
        let html = "";
        for (let j = 0; j < numbers.length; j++) {
            //let cellContent = "";
            let cellColor = "blue";
            let text ="";
            for (let k = 0; k < locations.length; k++) {

                if ((locations[k].cell.localeCompare(letters[i]+numbers[j]))==0) {

                    if((locations[k].cell.localeCompare(oponentShots[i]))==0){
                         cellColor= "black";

                    }else{
                         cellColor = "gray";
                    }
                }

            }
            html = html + "<td style='background-color: " + cellColor + "'></td>";
        }
       // console.log(html);
        return html;
    }
    function getColumnsGridSalvo(i){
             let html = "";
            for (let j = 0; j < numbers.length; j++) {
                //let cellContent = "";
                let cellColor = "blue";
                for (let k = 0; k < locationsSalvo.length; k++) {

                    if ((locationsSalvo[k].localeCompare(letters[i]+numbers[j]))==0) {
                        debugger;
                        //cellContent = "si";
                        cellColor = "gray";
                    }
                }
                html = html + "<td style='background-color: " + cellColor + "'></td>";
            }
           // console.log(html);
            return html;

    }

    // genera el HTML de las filas (depende de getColumnsHtml)
    function getRowsHtml(isGridShip) {
        let html = "";
        if(isGridShip){
        for (let i = 0; i < letters.length; i ++) {
            html = html + "<tr><th>" + letters[i] + "</th>" + getColumnsHtml(i) + "</tr>";
        }
        }else{
        debugger;
        for (let i = 0; i < letters.length; i ++) {
            html = html + "<tr><th>" + letters[i] + "</th>" +getColumnsGridSalvo(i) + "</tr>";
           }

        }
        return html;
    }

    // dibuja las filas de la grilla
    function renderRows() {
        document.getElementById("grid-letters").innerHTML = getRowsHtml(true);
        document.getElementById("grid-salvo-letters").innerHTML = getRowsHtml(false);
    }

    // Dibuja la grilla.
    function renderTable() {
        renderHeaders();
        renderRows();
    }

    // muestra los datos de los jugadores de la partida
    function showPlayersData(data) {
        let thisPlayer;
        let otherPlayer;
        let gamePlayer1 = data.gamePlayers[0];
        let gamePlayer2 = data.gamePlayers[1];
        // según el ID del gameplayer actual asigna thisPlayer al jugador correspondiente, otherPlayer al contrincante
        if (gamePlayer1.id == urlParams.get('gp')) {
            thisPlayer = gamePlayer1.player.email;
            otherPlayer = gamePlayer2.player.email;
        }
        else {
            thisPlayer = gamePlayer1.player.email;
            otherPlayer = gamePlayer2.player.email;
        }
        document.getElementById("players-data").innerHTML = thisPlayer + " (you) vs " + otherPlayer;
    }

    // recibe los datos del gameplayer y setea en el array locations las posiciones de todos los barcos del jugador
    function setLocations(data) {
        mappedLocations = data.ships.map(function(ship) { return ship.locations });
        locations = [].concat.apply([], mappedLocations);
        console.log(data.ships);
        console.log(mappedLocations);
    }

    function setSalvoLocations(data){

             for(let i =0;i<data.salvoes.length; i++){
                if(data.salvoes[i].playerID==urlParams.get('gp')){
                    locationsSalvo= locationsSalvo.concat(data.salvoes[i].locations);}
                 else{
                    oponentShots = oponentShots.concat(data.salvoes[i].locations);
                 }
             }
           // locationsSalvo = [].concat.apply([], mappedLocations);
            console.log("oponent's shots:"+oponentShots);
           // console.log("mapped Locations:"+mappedLocations);
            console.log("Locations salvo:"+locationsSalvo);

    }

    // carga los datos del gameplayer según el parámetro 'gp' en la URL y llama a los métodos que dibujan la grilla
    function loadData() {
        $.get("/api/game_view/"+urlParams.get('gp'))
            .done(function(data) {
                console.log(data);

                setLocations(data);
                setSalvoLocations(data);
                showPlayersData(data);
                renderTable();
            })
            .fail(function( jqXHR, textStatus ) {
                alert( "Failed: " + textStatus );
            });
    }

    loadData();

});