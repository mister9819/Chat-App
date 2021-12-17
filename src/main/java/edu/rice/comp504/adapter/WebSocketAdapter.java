package edu.rice.comp504.adapter;

import com.google.gson.internal.LinkedTreeMap;
import edu.rice.comp504.model.store.ChatAppStore;
import edu.rice.comp504.model.store.ChatRoomStore;
import edu.rice.comp504.model.store.UserStore;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.List;

import static j2html.TagCreator.p;


/**
 * Create a web socket for the server.
 */
@WebSocket
public class WebSocketAdapter {

    /**
     * Open user's session.
     *
     * @param session The user whose session is opened.
     */
    @OnWebSocketConnect
    public void onConnect(Session session) {
        List<String> userId = session.getUpgradeRequest().getParameterMap().get("userId");
        if (userId.size() == 1) {
            ChatAppStore.make().onSession(Integer.parseInt(userId.get(0)), session);
        }
    }

    /**
     * Close the user's session.
     *
     * @param session The use whose session is closed.
     */
    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        List<String> userId = session.getUpgradeRequest().getParameterMap().get("userId");
        if (userId.size() == 1) {
            ChatAppStore.make().offSession(Integer.parseInt(userId.get(0)));
        }
    }

    /**
     * Send a message.
     *
     * @param session The session user sending the message.
     * @param message The message to be sent.
     */
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
//        MsgToClientSender.broadcastMessage(UserDB.getUser(session),message);
    }
}
