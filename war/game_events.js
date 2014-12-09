function play_move(game_url, player, id_outer, id_inner) {
    $.get('PlayerMove',{game_url:game_url, player:player, id_inner:id_inner, id_outer:id_outer},
          function(response) {
              var js = JSON.parse(response);

              if (js.success == "true") {
                  if (document.getElementById("opponentRadioSelf").checked) {
                      /* Handled elsewhere (in game_state.on('value')) */
                  } else if (document.getElementById("opponentRadioAI").checked) {
                      play_move_ai(game_ref.toString());
                  }
              }
          }
         );
}

function play_move_ai() {

    if (document.getElementById("opponentRadioSelf").checked) {
        alert("PLAYING: " + game_ref + " " + self_player);
    } else {
        alert("PLAYING: " + game_ref + " " + get_opposing_player(self_player));
    }

}

function self_is_only_player() {

    var plr_x = null;
    var plr_o = null;

    game_seat.once('value', function(snapshot) {
        snapshot.forEach(function(snap) {
            plr_x = plr_x || snap.val().x;
            plr_o = plr_o || snap.val().o;
        });
    });

    if (plr_x == self_player) {
        return plr_o == null;
    } else if (plr_o == self_player) {
        return plr_x == null;
    }

    return ((plr_x != null) && (plr_o != null));
}

function handle_ai_button_press() {

    if (self_is_only_player(game_seat)) {
        play_move_ai();
    } else {
        document.getElementById("opponentRadioHuman").checked = true;
        document.getElementById("opponentRadioAI").checked = false;
    }
}

function set_self_to_current_player() {
    self_player = player_highlight;
    if (self_player == "x") {
        game_seat_self.set({o: "o"})
    } else {
        game_seat_self.set({x: "x"})
    }
}
