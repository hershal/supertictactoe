package main.java;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import com.supertictactoe.web.*;

public class Main extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

      showHome(req,resp);
  }

  private void showHome(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.getWriter().print("Hello from Java!");
  }

  public static void main(String[] args) throws Exception{
    Server server = new Server(Integer.valueOf(System.getenv("PORT")));
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

    context.setContextPath("/");
    context.setResourceBase("./war");
    context.setWelcomeFiles(new String[]{"SuperTicTacToe.html"});

    server.setHandler(context);
    context.addServlet(new ServletHolder(new Main()), "/Main");
    context.addServlet(new ServletHolder(new AIMoveServlet()), "/AIMove");
    context.addServlet(new ServletHolder(new PlayerMoveServlet()), "/PlayerMove");
    context.addServlet(new ServletHolder(new DefaultServlet()), "/*");

    server.start();
    server.join();
  }
}
