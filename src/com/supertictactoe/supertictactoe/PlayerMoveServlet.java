package com.supertictactoe.supertictactoe;

import static com.supertictactoe.Common.SuperTicTacToeStrings.*;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.ibm.icu.util.BytesTrie.Iterator;

import com.supertictactoe.supertictactoe.components.*;

import static com.supertictactoe.Common.StoredProcedure.*;
import static com.supertictactoe.Common.SuperTicTacToeStrings.*;

public class PlayerMoveServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        /* STUB */
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        final String game_url = request.getParameter("game_url");
        final String player = request.getParameter("player");
        final String id_outer_str = request.getParameter("id_outer");
        final String id_inner_str = request.getParameter("id_inner");

        Move move = null;

        if ((game_url == null) || (player == null) ||
            (id_outer_str == null) || (id_inner_str == null)) {

            return;
        } 
        int id_outer = Integer.parseInt(id_outer_str);
        int id_inner = Integer.parseInt(id_inner_str);

        id_outer = Integer.parseInt(id_outer_str);
        id_inner = Integer.parseInt(id_inner_str);
        move = new Move(id_outer, id_inner, SPBuildContender(player).getTeam());

        System.out.println(game_url + " " + move.toString());

        /* create the firebase reference */
        try {
            Firebase firebase = new Firebase(game_url);

            FirebaseResponse firebaseResponse = firebase.get();

            Game gm = SPParseFirebase(firebaseResponse);

            if (gm.play(move)) {
                System.out.println("VALID MOVE PLAYED!");
            } else {
                System.out.println("INVALID MOVE IGNORED!");
                /* Send response that move is not valid */
            }

        } catch (FirebaseException e) {
            /* STUB */
            e.printStackTrace();
        }
    }
}
