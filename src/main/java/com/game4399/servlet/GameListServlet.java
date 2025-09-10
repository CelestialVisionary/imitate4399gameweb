package com.game4399.servlet;

import com.game4399.dao.GameDAO;
import com.game4399.model.Game;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 游戏列表Servlet，用于处理前端游戏列表数据请求
 */
@WebServlet(name = "GameListServlet", urlPatterns = "/api/games")
public class GameListServlet extends HttpServlet {
    private GameDAO gameDAO;
    private Gson gson;

    @Override
    public void init() {
        gameDAO = new GameDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<Game> games = gameDAO.getAllGames();
            String json = gson.toJson(games);
            out.print(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson("获取游戏列表失败: " + e.getMessage()));
        } finally {
            out.close();
        }
    }
}