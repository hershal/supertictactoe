function play_move(game_url, player, id_outer, id_inner) {

    $.get('PlayerMove', {game_url:game_url, player:player, id_inner:id_inner, id_outer:id_outer},
          function(response) {
              var js = JSON.parse(response);

              console.log("player: " + response);
              if (js.success == "true") {
                  if (document.getElementById("opponentRadioAI").checked) {
                      /* console.log("play_move: calling play_move_ai"); */
                      play_move_ai(game_url, player, 0);
                  }
              }
          });
}

function play_move_ai(game_url, player_just_played, ai_difficulty) {

    $.get('AIMove', {game_url:game_url, player_just_played:player_just_played, ai_difficulty:ai_difficulty},
          function(response) {
              var js = JSON.parse(response);
              console.log("ai: " + response);
          });
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
        console.log(self_player);
        console.log(player_highlight);
        if (self_player != player_highlight) {
            play_move_ai(game_ref.toString(), self_player, 0);
        }
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

function handle_session_jump_input() {

    jump_to_session(document.getElementById("session-id-input").value);
}

function handle_reset_button() {

    game_ref.remove();
    game_random_ref.remove();
    window.location.reload();
}

function handle_allow_rand_checkbox() {

    if (document.getElementById("allow_random_checkbox").checked == true) {
        if (is_only_player(self_player) == true) {
            game_random_ref.set(game_random_ref.name());
            game_client_state.child("allow_random").set("1");
            console.log("random game ref created " + game_random_ref.name());
        } else {
            document.getElementById("allow_random_checkbox").checked = false;
            console.log("can't allow random players, already have two players");
        }
    } else {
        game_client_state.child("allow_random").remove();
        game_random_ref.remove();
        console.log("random game ref deleted " + game_random_ref.name());
    }
}

function handle_random_game_button() {

    game_random_ref.parent().once('value', function(snap) {
        if (snap.hasChildren()) {
            snap.forEach(function(ss) {
                if (ss.val() != get_current_session_id()) {
                    jump_to_session(ss.val());
                }
            });
        } else {
            jump_to_session(game_ref.push().name());
        }
    });

}

function set_self_to_current_player() {

    self_player = player_highlight;
    if (self_player == "x") {
        game_seat_self.set({x: "x"})
    } else {
        game_seat_self.set({o: "o"})
    }
    c1.set_occupant(self_player);
    c1.draw(p_ctx);
}

function disable_single_player() {

    document.getElementById("opponentRadioHuman").checked = true;
    $("#opponentRadioAI").prop("disabled", true);
    $("#opponentRadioSelf").prop("disabled", true);
    console.log("disabled single player");
}

function enable_single_player() {

    $("#opponentRadioAI").prop("disabled", false);
    $("#opponentRadioSelf").prop("disabled", false);
    console.log("enabled single player");
}

function disable_allow_random() {

    $("#allow_random_checkbox").prop("disabled", true);
    game_random_ref.remove();
    console.log("disabled allow random");
}

function enable_allow_random() {

    $("#allow_random_checkbox").prop("disabled", false);
    game_random_ref.set(game_random_ref.name());
    console.log("enabled allow random");
}
