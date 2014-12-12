function play_move(game_url, player, id_outer, id_inner) {

    $.get('PlayerMove', {game_url:game_url, player:player, id_inner:id_inner, id_outer:id_outer},
          function(response) {
              var js = JSON.parse(response);

              if (js.success == "true") {
                  console.log("player move successful");
                  console.log(response);
                  if (document.getElementById("opponentRadioAI").checked) {
                      console.log("play_move: calling play_move_ai");
                      play_move_ai(game_url, player, 0);
                  }
              } else {
                  console.log("player move failed");
                  console.log(response);
              }
          });
}

function play_move_ai(game_url, player_just_played, ai_difficulty) {

    console.log("attempting AI move: " + game_url +
                " player_just_played: " + player_just_played +
                " diff: " + ai_difficulty);

    $.get('AIMove', {game_url:game_url, player_just_played:player_just_played, ai_difficulty:ai_difficulty},
          function(response) {
              var js = JSON.parse(response);
              if (js.success == "true") {
                  console.log("ai move successful");
              } else {
                  console.log(response);
              }
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

function disable_single_player() {

    document.getElementById("opponentRadioHuman").checked = true;
    $("#opponentRadioAI").prop("disabled", true);
    $("#opponentRadioSelf").prop("disabled", true);
}

function enable_single_player() {

    $("#opponentRadioAI").prop("disabled", false);
    $("#opponentRadioSelf").prop("disabled", false);
}
