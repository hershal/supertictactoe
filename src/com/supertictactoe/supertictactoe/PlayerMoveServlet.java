package com.supertictactoe.supertictactoe;

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

        if ((player == null) || (id_outer_str == null) || (id_inner_str == null)) {
            /* DO SOMETHING HERE */
            return;
        }

        int id_outer = Integer.parseInt(id_outer_str);
        int id_inner = Integer.parseInt(id_inner_str);

        System.out.println(game_url + " " + player + " " + id_outer + " " + id_inner);

        /* create the firebase reference */
        try {
            Firebase firebase = new Firebase(game_url);

            FirebaseResponse firebaseResponse = firebase.get();

            System.out.println(firebaseResponse);

            Set<Entry<String, Object>> firebaseResponseBody = firebaseResponse.getBody().entrySet();

            java.util.Iterator<Entry<String, Object>> interMoveIter
                = firebaseResponse.getBody().entrySet().iterator();

            while(interMoveIter.hasNext()) {
                Map.Entry interMovePair = (Map.Entry)interMoveIter.next();

                System.out.println("inter: " + interMovePair.getKey());

                if (interMovePair.getValue() instanceof String) {
                    System.out.println("string: " + interMovePair.getValue());
                } else {
                    java.util.Iterator<Entry<String, Object>> intraMoveIter
                        = ((Map<String, Object>) interMovePair.getValue()).entrySet().iterator();
                    while(intraMoveIter.hasNext()) {
                        Map.Entry intraMovePair = (Map.Entry)intraMoveIter.next();
                        System.out.println(intraMovePair.getKey() + " = " + intraMovePair.getValue());
                        /* TODO: */
                        /* Need to create a package to take a Map of the
                         * entries in and generate the board state */
                    }
                }
            }

        } catch (FirebaseException e) {
            /* STUB */
            e.printStackTrace();
        }
    }
}
