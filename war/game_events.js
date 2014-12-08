function play_move(game_url, player, id_outer, id_inner) {
    $.get('PlayerMove',{game_url:game_url, player:player, id_inner:id_inner, id_outer:id_outer},
          function(response) {
              var js = JSON.parse(response);

              if (js.success == "true") {
                  var old_self_player = self_player;
                  if (document.getElementById("opponentRadioSelf").checked) {

                  }
              }
          }
         );
}
