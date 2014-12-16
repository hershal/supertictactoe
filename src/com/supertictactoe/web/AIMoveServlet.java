package com.supertictactoe.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.supertictactoe.components.*;
import com.supertictactoe.components.StrategyFactory.StrategyType;

import static com.supertictactoe.common.StoredProcedure.*;

public class AIMoveServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        /* STUB */
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        final String game_url = request.getParameter("game_url");
        final String player_just_played = request.getParameter("player_just_played");
        final String ai_difficulty_str = request.getParameter("ai_difficulty");

        JSONObject json = new JSONObject();

        if ((game_url == null) || (player_just_played == null) ||
            (ai_difficulty_str == null)) {
            try {
                /* Stupid java */
                json.append("success", "false");
                json.append("reson", "null_data");
                json.write(response.getWriter());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
        }

        int ai_difficulty = Integer.parseInt(ai_difficulty_str);

        /* create the Firebase reference */
        try {
            Firebase firebase = new Firebase(game_url);
            
            Bot bot;

            if (ai_difficulty > 0) {
                bot = new Bot(SPGetOpposingSide(SPBuildContender(player_just_played).getTeam()),
                              StrategyType.PERFECT);
            } else {
                bot = new Bot(SPGetOpposingSide(SPBuildContender(player_just_played).getTeam()),
                              StrategyType.RANDOM);
            }

            Game gm = SPParseFirebase(firebase);
            Move move = bot.nextMove(gm);
            
            if (gm.play(move)) {
                System.out.println("AI VALID MOVE PLAYED!");
                SPPushMove(firebase, move);
                SPUpdateState(firebase, gm, move);
                json.append("success", "true");
                json.append("id_outer", move.getBoard() + "");
                json.append("id_inner", move.getCell() + "");
            } else {
                System.out.println("AI INVALID MOVE IGNORED!");
                json.append("success", "false");
                json.append("reason", "ai_invalid_move");
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
                json.append("reason", "ai_firebase_exception");
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
                json.append("reason", "ai_json_exception");
                json.write(response.getWriter());
            } catch (JSONException jse) {
                // TODO Auto-generated catch block
                jse.printStackTrace();
            }
        }
    }
}
