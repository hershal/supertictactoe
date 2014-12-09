function play_move(game_url, player, id_outer, id_inner) {
    $.get('PlayerMove',{game_url:game_url, player:player, id_inner:id_inner, id_outer:id_outer},
          function(response) {
              var js = JSON.parse(response);

              if (js.success == "true") {
                  if (document.getElementById("opponentRadioSelf").checked) {
                      /* Handled elsewhere (in game_state.on('value')) */
                  } else if (document.getElementById("opponentRadioAI").checked) {
                      play_move_ai(game_url);
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

function is_only_player(player) {

    var plr_x = null;
    var plr_o = null;

    game_seat.once('value', function(snapshot) {
        snapshot.forEach(function(snap) {
            plr_x = plr_x || snap.val().x;
            plr_o = plr_o || snap.val().o;
        });
    });

    alert(player + " " + plr_x + " " + plr_o);

    if (plr_x == player) {
        return plr_o == null;
    } else if (plr_o == player) {
        return plr_x == null;
    }

    return ((plr_x != null) && (plr_o != null));
}

/* This function is dirtier because it's wired to a radio button */
function handle_ai_button_press() {

    if (is_only_player(self_player)) {
        play_move_ai(game_ref.toString());
    } else {
        document.getElementById("opponentRadioHuman").checked = true;
        document.getElementById("opponentRadioAI").checked = false;
        document.getElementById("opponentRadioSelf").checked = false;
    }
}

function handle_self_button_press() {

    if (is_only_player(self_player)) {
        set_self_to_current_player();
    } else {
        document.getElementById("opponentRadioHuman").checked = true;
        document.getElementById("opponentRadioAI").checked = false;
        document.getElementById("opponentRadioSelf").checked = false;
    }
}

function set_self_to_current_player() {

    self_player = player_highlight;
    if (self_player == "x") {
        game_seat_self.set({x: "x"})
    } else {
        game_seat_self.set({o: "o"})
    }
    c1.set_occupant(self_player);
    c1.draw(ctx);
}
