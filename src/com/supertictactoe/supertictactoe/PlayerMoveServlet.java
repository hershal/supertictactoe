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

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
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
            JSONObject json = new JSONObject();
            Firebase firebase = new Firebase(game_url);

            Game gm = SPParseFirebase(firebase);

            if (gm.play(move)) {
                System.out.println("VALID MOVE PLAYED!");
                SPPushFirebase(firebase, move);
                json.append("success", "true");
            } else {
                System.out.println("INVALID MOVE IGNORED!");
                json.append("success", "false");
                /* Send response that move is not valid */
            }
            json.write(response.getWriter());
        } catch (FirebaseException e) {
            /* STUB */
            e.printStackTrace();
        } catch (JSONException e) {
            // This is stupid; adding to a JSON object is NOT exceptional!
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
