package com.supertictactoe.supertictactoe;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.supertictactoe.supertictactoe.components.*;

import static com.supertictactoe.Common.StoredProcedure.*;

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
        JSONObject json = new JSONObject();

        if ((game_url == null) || (player == null) ||
            (id_outer_str == null) || (id_inner_str == null)) {
            System.out.println("Received null data");
            try {
                /* Stupid java */
                json.append("success", "false");
                json.write(response.getWriter());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
        }
        int id_outer = Integer.parseInt(id_outer_str);
        int id_inner = Integer.parseInt(id_inner_str);

        id_outer = Integer.parseInt(id_outer_str);
        id_inner = Integer.parseInt(id_inner_str);
        move = new Move(id_outer, id_inner, SPBuildContender(player).getTeam());

        System.out.println(game_url + " " + move.toString());

        /* create the Firebase reference */
        try {
            Firebase firebase = new Firebase(game_url);

            Game gm = SPParseFirebase(firebase);

            if (gm.play(move)) {
                System.out.println("VALID MOVE PLAYED!");
                SPPushMove(firebase, move);
                SPUpdateState(firebase, gm, move);
                json.append("success", "true");
                json.append("id_outer", move.getBoard() + "");
                json.append("id_inner", move.getCell() + "");
            } else {
                System.out.println("INVALID MOVE IGNORED!");
                json.append("success", "false");
                json.append("reason", "invalid_move");
                json.append("id_outer", move.getBoard() + "");
                json.append("id_inner", move.getCell() + "");
            }
            json.write(response.getWriter());
        } catch (FirebaseException e) {
            /* STUB */
            e.printStackTrace();
            try {
                /* Stupid java */
                json.append("success", "false");
                json.append("reason", "firebase_exception");
                json.write(response.getWriter());
            } catch (JSONException jse) {
                // TODO Auto-generated catch block
                jse.printStackTrace();
            }
        } catch (JSONException e) {
            // This is stupid; adding to a JSON object is NOT exceptional!
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                /* Stupid java */
                json.append("success", "false");
                json.append("reason", "json_exception");
                json.write(response.getWriter());
            } catch (JSONException jse) {
                // TODO Auto-generated catch block
                jse.printStackTrace();
            }
        }
    }
}
