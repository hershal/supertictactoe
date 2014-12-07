function check_valid_move(game_url, player, id_outer, id_inner) {
    $.get('PlayerMove',{game_url:game_url, player:player, id_inner:id_inner, id_outer:id_outer},
          function(response) {
              /* alert(response); */
          }
         );
}
