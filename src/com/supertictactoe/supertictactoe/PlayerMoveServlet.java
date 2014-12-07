package com.supertictactoe.supertictactoe;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlayerMoveServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        /* STUB */
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        final String game_id = request.getParameter("game_id");
        final String player = request.getParameter("player");
        final String id_outer_str = request.getParameter("id_outer");
        final String id_inner_str = request.getParameter("id_inner");

        if ((player == null) || (id_outer_str == null) || (id_inner_str == null)) {
            /* DO SOMETHING HERE */
            return;
        }

        int id_outer = Integer.parseInt(id_outer_str);
        int id_inner = Integer.parseInt(id_inner_str);

        System.out.println(game_id + " " + player + " " + id_outer + " " + id_inner);

    }
}
