function check_valid_move(game_id, player, id_outer, id_inner) {
    /* alert(game_ref); */
    $.get('PlayerMove',{game_id:game_id, player:player, id_inner:id_inner, id_outer:id_outer},
          function(response) {
              /* alert(response); */
          }
         );
}
