<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>

        <!-- Bootswatch "Paper" theme -->
        <link type="text/css" rel="stylesheet" href="/bootstrap.css" />

        <!-- JQuery for dropdowns and such -->
        <script src="/jquery-2.1.0.min.js"></script>

        <!-- Latest compiled and minified JavaScript for Bootstrap -->
        <script src="/bootstrap.min.js"></script>

        <!-- Bootstrap Glyphicons -->
        <link href="/bootstrap-glyphicons.css" rel="stylesheet">

        <!-- Timeago JQuery plugin -->
        <!-- <script src="http://timeago.yarp.com/jquery.timeago.js"></script> -->

        <!-- Helper functions -->
        <script src="/helper.js"></script>

        <!-- Game board functions -->
        <script src="/game_board.js"></script>

        <!-- Game board Event functions -->
        <script src="/game_events.js"></script>

        <!-- The Firebase data store -->
        <script src='https://cdn.firebase.com/js/client/1.1.1/firebase.js'></script>

    </head>

    <body>
        <!-- Navbar -->
        <br />
        <div class="container">
            <div class="navbar navbar-inverse">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle"
                            data-toggle="collapse" data-target=".navbar-responsive-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/">SuperTicTacToe</a>
                </div>
                <div class="navbar-collapse collapse navbar-responsive-collapse">
                    <ul class="nav navbar-nav">
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">

                            <!-- User login/logout button -->
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Menu <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li class="divider"></li>
                                <li><a href="#" data-toggle="modal" data-target=".subscribe-form">Subscribe</a></li>
                                <li><a href="#" data-toggle="modal" data-target=".unsubscribe-form">Unsubscribe</a></li>
                            </ul>
                        </li>
                    </ul>

                </div>
            </div>
        </div>

        <div class="container">
            <!-- Alert Area -->
            <div id="alert-area"></div>


            <!-- Main body -->
            <div class="main">

                <!-- The main game -->
                <div class="row">
                    <div class="col-md-9">
                        <div class="panel panel-primary">
                            <div class="panel-body">
                                <canvas id="game_board_canvas" width="800" height="600">
                                    Canvas Tag not supported
                                </canvas>

                            </div>
                        </div>
                    </div>

                    <!-- The sidebar -->
                    <div class="col-md-3">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">Options</h3>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <div class="col-lg-2">
                                        <p><strong>Opponent:</strong></p>
                                    </div><br/>
                                    <div class="col-lg-10">
                                        <div class="radio">
                                            <label><input type="radio" name="opponentRadios" id="opponentRadioHuman" value="Human" checked="">Human</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="opponentRadios" id="opponentRadioAI" value="AI" onclick="js:handle_ai_button_press()">AI</label>
                                        </div>
                                        <div class="radio">
                                            <label><input type="radio" name="opponentRadios" id="opponentRadioSelf" value="Self" onclick="js:handle_self_button_press()">Self</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="jumbotron">
                <h1><font color="#2196f3">Super</font>TicTacToe</h1>

                <h2>About SuperTicTacToe:</h2>
                <p>SuperTicTacToe is a modern take on the classic Tic Tac Toe...</p>
                <h2>How to Play:</h2>
                <p>SuperTicTacToe is played...</p>

                <!-- <ul class="nav nav-tabs"">
                <li class="active"><a href="#about-supertictactoe" data-toggle="tab">About SuperTicTacToe</a></li>
                <li><a href="#how-to-play" data-toggle="tab">How to Play</a></li>
                </ul>
                <div id="lowfold-tabs" class="tab-content">
                <div class="tab-pane fade active in" id="about-supertictactoe">
                <h2>About SuperTicTacToe:</h2>
                <p>SuperTicTacToe is a modern take on the classic Tic Tac Toe...</p>
                </div>
                <div class="tab-pane fade" id="how-to-play">
                <h2>How to Play:</h2>
                <p>SuperTicTacToe is played...</p>
                </div>
                </div> -->
            </div>


            <footer>
                <div class="row">
                    <div class="col-lg-12 text-center">
                        <p>This is the footer</p>
                    </div>
                </div>
            </footer>
        </div>


        <!-- Don't put too much here!! -->
        <script>
         var game_ref = init_session_id();
         var game_moves = game_ref.child("moves");
         var game_state = game_ref.child("state");
         var game_seat = game_ref.child("seats");
         var game_seat_self = "Human";

         var last_radio = null;

         var player_x = null;
         var player_o = null;

         /* Spectator is null */
         var player_highlight;
         var self_player = null;

         var canvas = document.getElementById("game_board_canvas");
         var ctx = canvas.getContext("2d");

         var c1 = new cell(10, 10, 20, 20);
         var sb1 = new superboard(160, 10, 500, 400);

         $('input[name$="opponentRadios"]').change(function(){
             last_radio = $(this).val();

             /* Why do I need this? */
             if ($(this).val() == "Self") {
                 c1.set_occupant(self_player);
                 c1.draw(ctx);
             }
         })

         game_moves.on('child_added', function(snapshot) {
             var pkg = snapshot.val();
             if ((pkg.id_inner != null) && (pkg.id_outer != null) && (pkg.player != null)) {
                 sb1.getCell(pkg.id_outer, pkg.id_inner).set_occupant(pkg.player);
                 sb1.draw(ctx);
             }
         });

         game_seat.once('value', function(snapshot) {

             snapshot.forEach(function(snap) {
                 player_x = player_x || snap.val().x;
                 player_o = player_o || snap.val().o;
             });

             if (player_x == null) {
                 self_player = "x";
                 game_seat_self = game_seat.push({x: "x"});
                 game_seat_self.onDisconnect().remove();
                 /* alert("PLACEHOLDER:\nplayer is x"); */
             } else if (player_o == null) {
                 self_player = "o";
                 game_seat_self = game_seat.push({o: "o"});
                 game_seat_self.onDisconnect().remove();
                 /* alert("PLACEHOLDER:\nplayer is o\n"); */
             } else {
                 alert("PLACEHOLDER:\nyou are spectating");
             }

             if (!is_only_player(self_player)) {
                 disable_single_player();
             }

             c1.set_occupant(self_player);
             c1.draw(ctx);
         });

         game_seat.on('child_added', function(snapshot) {
             /* When another human has been added switch to that */
             if (!is_only_player(self_player)) {
                 disable_single_player();
             }
         });

         game_seat.on('child_removed', function(snapshot) {
             enable_single_player();
         });

         game_state.on('value', function(snapshot) {
             var pkg = snapshot.val();
             player_highlight = (snapshot.child("current_player").val() || "x");

             if (document.getElementById("opponentRadioSelf").checked) {
                 set_self_to_current_player();
             }

             c1.set_occupant(self_player);
             c1.draw(ctx);

             var game_state_boards_avail = game_state.child("boards_avail");
             var game_state_boards_won = game_state.child("boards_won");
             sb1.reset_highlights();

             game_state_boards_won.on("value", function(snap) {
                 snap.forEach(function(ss) {
                     sb1.boards[ss.name()].set_winner(ss.val());
                 });
             });
             game_state_boards_avail.once("value", function(snap) {
                 snap.forEach(function(ss) {
                     sb1.highlight_board(ss.val(), player_highlight);
                 });
             });
             sb1.draw(ctx);
         });

         var oldCellAt = null;
         canvas.addEventListener('mousemove', function(evt) {
             var pos = getMousePos(canvas, evt);

             if (oldCellAt != null) {
                 oldCellAt.val.set_hover(false);
             }

             var cellAt = sb1.getCellAt(pos.x, pos.y);
             if (cellAt != null) {
                 cellAt.val.set_hover(true);
             }
             oldCellAt = cellAt;
             sb1.draw(ctx);
         }, false);

         canvas.addEventListener('mousedown', function(evt) {
             if (self_player != player_highlight) {
                 /* alert("PLACEHOLDER:\nIt's not your turn.\ncurrent_player: " + player_highlight + " you: " + self_player); */
                 return;
             }
             var pos = getMousePos(canvas, evt);
             var cellAt = sb1.getCellAt(pos.x, pos.y);
             if (cellAt != null) {
                 play_move(game_ref.toString(), self_player,
                           cellAt.id_outer, cellAt.id_inner);
             }
             sb1.draw(ctx);
         }, false);


        </script>
    </body>
</html>
