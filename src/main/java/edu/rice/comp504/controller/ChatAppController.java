package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.WebSocketAdapter;
import edu.rice.comp504.adapter.ChatRoomAdapter;
import edu.rice.comp504.model.chatroom.AChatRoom;
import spark.QueryParamsMap;

import static spark.Spark.*;

/**
 * The chat app controller communicates with all the clients on the web socket.
 */
public class ChatAppController {

    /**
     * Chat App entry point.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFiles.location("/public");
        webSocket("/chatapp", WebSocketAdapter.class);
        init();
        Gson gson = new Gson();
        ChatRoomAdapter ca = new ChatRoomAdapter();

        post("/login", (request, response) -> {
            QueryParamsMap map = request.queryMap();
            int userId = ca.login(map.value("username"), map.value("password"));
            return gson.toJson(userId);
        });

        post("/signup", (request, response) -> {
            QueryParamsMap map = request.queryMap();
            int userId = ca.signup(map.value("username"),
                    map.value("password"), Integer.parseInt(map.value("age")),
                    map.value("school"), map.value("interests"), map.value("avatar"));
            return gson.toJson(userId);
        });

        post("/logout", (request, response) -> {
            QueryParamsMap map = request.queryMap();
            int userId = Integer.parseInt(map.value("userId"));
//            ca.logout(userId);
            return gson.toJson("success");
        });

        get("/downloadInfo/:type", (request, response) -> {
            //download some information form the ChatApp
            //chatroom list, user list...
            //ca.downloadInfo(request.params(":type"));
            switch (request.params(":type")) {
                case "userList":
                    return gson.toJson(ca.getAllUser());
                case "roomList":
                    return gson.toJson(ca.getRoomList());
                default:
                    return gson.toJson("false instruction");
            }
        });

        get("/loadUserChatRoom", (request, response) -> {
//            QueryParamsMap map = request.queryMap();
            int userId = Integer.parseInt(request.queryParams("userId"));
            return gson.toJson(ca.getUserChatRoom(userId));
        });

        get("/leaveAllRoom", (request, response) -> {
//            QueryParamsMap map = request.queryMap();
            int userId = Integer.parseInt(request.queryParams("userId"));
            return gson.toJson(ca.leaveAllRoom(userId));
        });

        get("/loadJoinChatRoom", (request, response) -> {
//            QueryParamsMap map = request.queryMap();
            int userId = Integer.parseInt(request.queryParams("userId"));
            return gson.toJson(ca.getJoinChatRoom(userId));
        });

        get("/loadChatRoomMessage", (request, response) -> {
            int userId = Integer.parseInt(request.queryParams("userId"));
            int chatRoomId = Integer.parseInt(request.queryParams("chatRoomId"));
            return gson.toJson(ca.getUserMessage(userId, chatRoomId));
        });

        get("/loadDirectMessage", (request, response) -> {
            int userId = Integer.parseInt(request.queryParams("userId"));
            return gson.toJson(ca.getDirectMessage(userId));
        });

        get("/loadChatRoomUsers", (request, response) -> {

            int chatRoomId = Integer.parseInt(request.queryParams("chatRoomId"));
            return gson.toJson(ca.getUserList(chatRoomId));
        });

        get("/getUsername", (request, response) -> {
            int userId = Integer.parseInt(request.queryParams("userId"));
            return gson.toJson(ca.getUser(userId).getUsername());
        });

        get("/loadRoomAdmin", (request, response) -> {
            //create a new chatroom
            int chatRoomId = Integer.parseInt(request.queryParams("chatRoomId"));

            return gson.toJson(ca.getChatroom(chatRoomId).getAdmin());
        });

        post("/invitation", (request, response) -> {
            ca.inviteJoinChatroom(Integer.parseInt(request.queryMap().value("invitorId")),
                    Integer.parseInt(request.queryMap().value("receiverId")),
                    Integer.parseInt(request.queryMap().value("chatroomId")));
            return gson.toJson("invitation");
        });

        post("/uploadInfo", (request, response) -> {
            //TODO: upload some information to the ChatApp
            //user name, user profile, chatroom name, chatroom description...
            //ca.uploadInfo(request.queryMap().value("type"),
            //request.queryMap().value("info"))
            return gson.toJson("user create a new chatroom");
        });

        post("/createChatroom", (request, response) -> {
            //create a new chatroom
            QueryParamsMap map = request.queryMap();
            String roomId = ca.createRoom(Integer.parseInt(map.value("userId")), Integer.parseInt(map.value("size")), map.value("description"),
                    map.value("name"), map.value("type"), map.value("password")) + "";
            return gson.toJson(roomId);
        });

        post("/joinChatroom", (request, response) -> {
            String resp = ca.addUser(Integer.parseInt(request.queryMap().value("chatroomId")), Integer.parseInt(request.queryMap().value("userId")));
            return gson.toJson(resp);
        });


        post("/operationToChatroom", (request, response) -> {
            //make an operation to a chatroom
            //close, clear history...
            QueryParamsMap map = request.queryMap();
            int roomId = Integer.parseInt(map.value("roomId"));
            int userId = Integer.parseInt(map.value("userId"));
            String resp = "";
            switch (map.value("operation")) {
                case "leave":
                    System.out.println("user leave room");
                    resp = ca.leaveRoom(roomId, userId);
                    break;
                case "close":
                    resp = ca.closeRoom(roomId, userId);
                    break;
                default:
                    break;
            }
            return gson.toJson(userId + " user " + map.value("operation") + " operation To Chatroom " + roomId + ": " + resp);
        });

        post("/operationToUser", (request, response) -> {
            QueryParamsMap map = request.queryMap();
            int userId = Integer.parseInt(map.value("userId"));
            int roomId = Integer.parseInt(map.value("roomId"));
            String resp = "";
            switch (map.value("operation")) {
                case "remove":
                    resp = ca.leaveRoom(roomId, userId);
                    break;
                case "block":
                    resp = ca.blockUser(roomId, userId);
                    break;
                case "unblock":
                    resp = ca.unblockUser(roomId, userId);
                    break;
                case "setAdmin":
                    resp = ca.addAdmin(roomId, userId);
                    break;
                default:
                    break;
            }
            //block, unblock, remove, set admin...
            return gson.toJson(map.value("operation")
                    + " operation To User " + userId + ": " + resp);
        });

        post("/operationToMsg", (request, response) -> {
            //make an operation to a message
            QueryParamsMap map = request.queryMap();
            int userId = Integer.parseInt(map.value("userId"));
            int roomId = Integer.parseInt(map.value("roomId"));
            int msgId = Integer.parseInt(map.value("msgId"));
            ca.operateMessage(roomId, userId, msgId, map.value("operation"));

            return gson.toJson("operation To Chatroom");
        });

        post("/EditMsg", (request, response) -> {
            //make an operation to a message
            QueryParamsMap map = request.queryMap();
            int userId = Integer.parseInt(map.value("userId"));
            int roomId = Integer.parseInt(map.value("roomId"));
            int msgId = Integer.parseInt(map.value("msgId"));
            ca.editMessage(roomId, userId, msgId, map.value("value"));

            return gson.toJson("operation To Chatroom");
        });

        post("/sendMsg", (request, response) -> {
            //make an operation to a message
            QueryParamsMap map = request.queryMap();
            int userId = Integer.parseInt(map.value("userId"));
            int roomId = Integer.parseInt(map.value("roomId"));
            String type = map.value("type");
            int receiverId = Integer.parseInt(map.value("receiverId"));
            String data = map.value("data");
            int resp = ca.sendMessage(type, data, userId, roomId, receiverId);

            return gson.toJson("Msg Id: " + resp);
        });

    }

    /**
     * Get the heroku assigned port number.
     *
     * @return The heroku assigned port number
     */
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // return default port if heroku-port isn't set.
    }
}

