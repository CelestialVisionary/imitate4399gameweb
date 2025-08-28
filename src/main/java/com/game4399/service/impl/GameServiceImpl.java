package com.game4399.service.impl;

import com.game4399.model.Game;
import com.game4399.service.GameService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GameService接口的实现类，提供游戏相关的服务
 */
public class GameServiceImpl implements GameService {
    // 模拟数据库，存储游戏数据
    private static List<Game> gameDatabase = new ArrayList<>();
    
    // 静态初始化，添加一些模拟数据
    static {
        gameDatabase.add(new Game(1, "植物大战僵尸", "策略", "一款经典的塔防游戏，通过种植不同的植物来抵御僵尸的进攻。", "images/pvz.jpg", "play/pvz", 1234567, 4.8));
        gameDatabase.add(new Game(2, "王者荣耀", "MOBA", "一款多人在线战术竞技游戏，5V5对战。", "images/wzry.jpg", "play/wzry", 9876543, 4.6));
        gameDatabase.add(new Game(3, "迷你世界", "沙盒", "一款3D沙盒类游戏，玩家可以自由创造和破坏。", "images/mnsj.jpg", "play/mnsj", 5678901, 4.5));
        gameDatabase.add(new Game(4, "我的世界", "沙盒", "一款风靡全球的沙盒游戏，探索、建造、生存。", "images/mc.jpg", "play/mc", 8765432, 4.9));
        gameDatabase.add(new Game(5, "和平精英", "射击", "一款多人在线射击游戏，100人同时在线竞技。", "images/hpjy.jpg", "play/hpjy", 6543210, 4.4));
        gameDatabase.add(new Game(6, "奥拉星", "角色扮演", "一款经典的回合制角色扮演游戏。", "images/alx.jpg", "play/alx", 3210987, 4.3));
        gameDatabase.add(new Game(7, "赛尔号", "策略", "一款以收集和培养精灵为主题的策略游戏。", "images/seer.jpg", "play/seer", 4321098, 4.2));
        gameDatabase.add(new Game(8, "洛克王国", "角色扮演", "一款以魔法宠物为主题的角色扮演游戏。", "images/lkwg.jpg", "play/lkwg", 5432109, 4.1));
    }
    
    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(gameDatabase);
    }
    
    @Override
    public Game getGameById(int id) {
        return gameDatabase.stream()
                .filter(game -> game.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Game> getGamesByCategory(String category) {
        return gameDatabase.stream()
                .filter(game -> game.getCategory().equals(category))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Game> searchGames(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return gameDatabase.stream()
                .filter(game -> game.getName().toLowerCase().contains(lowerKeyword) || 
                        game.getDescription().toLowerCase().contains(lowerKeyword) ||
                        game.getCategory().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Game> getHotGames(int limit) {
        // 按照播放次数降序排列，取前limit个
        return gameDatabase.stream()
                .sorted((g1, g2) -> Integer.compare(g2.getPlayCount(), g1.getPlayCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}