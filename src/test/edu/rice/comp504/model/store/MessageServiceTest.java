package edu.rice.comp504.model.store;

import edu.rice.comp504.adapter.ChatRoomAdapter;
import edu.rice.comp504.model.User;
import edu.rice.comp504.model.message.*;
import edu.rice.comp504.utils.Types;
import org.junit.Assert;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MessageServiceTest {

    /**
     * Make the system sleep for 0.001 sec.
     */
    public void SystemSleep() {
        try {
            Thread.sleep( 1 );
        } catch (Exception e){
            System.out.println("the system don't want to sleep");
        }
    }

    /**
     * Test 1: test to broadcast all message types from the sender side.
     */
    @Test
    public void testBroadcastMsg() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user3 = CA.signup("user3", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user4 = CA.signup("user4", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user5 = CA.signup("user5", "12345", 22, "Rice", "Eating", "avatar1.png");
        int roomId = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
        CA.addUser(roomId, user1);
        SystemSleep();
        CA.addUser(roomId, user2);
        SystemSleep();
        CA.addUser(roomId, user3);
        SystemSleep();
        CA.addUser(roomId, user4);
        SystemSleep();
        CA.addUser(roomId, user5);
        SystemSleep();

        //Test null message
        AMessage nullMsg = MessageService.make().makeMessage("default", "nothing", UserStore.make().getUser(user1),
                ChatRoomStore.make().getRoom(roomId), UserStore.make().getUser(user2));
        Assert.assertEquals("Test null message id", 0, nullMsg.getId());
        Assert.assertEquals("Test null message type", "null", nullMsg.getType());

        //Test broadcast for Text Message
        ArrayList<String> textData = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            textData.add("Broadcast Text Message" + i);
        }
        ArrayList<Integer> res1 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            res1.add(CA.sendMessage("text", textData.get(i), user1, roomId, user1));
            SystemSleep();
        }
        for (int i = 0; i <= 5; i++) {
            Assert.assertEquals("Test broadcast for text message"+i, textData.get(i),
                    ((TextMessage)CA.getChatroom(roomId).getMessageMap().get(res1.get(i))).getText());
        }

        //Test broadcast for Image Message
        ArrayList<String> imgData = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            imgData.add("Broadcast Image Message Url " + i);
        }
        ArrayList<Integer> res2 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            res2.add(CA.sendMessage("image", imgData.get(i), user1, roomId, user1));
            SystemSleep();
        }
        for (int i = 0; i <= 5; i++) {
            Assert.assertEquals("Test broadcast for image message"+i, imgData.get(i),
                    ((ImageMessage)CA.getChatroom(roomId).getMessageMap().get(res2.get(i))).getText());
        }

        //Test broadcast for Link Message
        ArrayList<String> linkData = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            linkData.add("Broadcast Link Message " + i);
        }
        ArrayList<Integer> res3 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            res3.add(CA.sendMessage("link", linkData.get(i), user1, roomId, user1));
            SystemSleep();
        }
        for (int i = 0; i <= 5; i++) {
            Assert.assertEquals("Test broadcast for link message" + i, linkData.get(i),
                    ((LinkMessage) CA.getChatroom(roomId).getMessageMap().get(res3.get(i))).getText());
        }
    }

    /**
     * Test 2: test to block a user so that it cannot send any messages.
     */
    @Test
    public void testBlockUserSendingMsg() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user3 = CA.signup("user3", "12345", 22, "Rice", "Eating", "avatar1.png");
        int roomId = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
        CA.addUser(roomId, user1);
        SystemSleep();
        CA.addUser(roomId, user2);
        SystemSleep();
        CA.addUser(roomId, user3);

        CA.blockUser(roomId, user1);
        Assert.assertTrue("Check block user", CA.getChatroom(roomId).checkBlock(user1));

        ArrayList<Integer> res = new ArrayList<>();
        res.add(CA.sendMessage("text", "TEXT", user1, roomId, user1));
        res.add(CA.sendMessage("image", "IMAGE", user1, roomId, user1));
        res.add(CA.sendMessage("link", "LINK", user1, roomId, user1));

        for (int i = 0; i <= 2; i++) {
            Assert.assertEquals("Test Block User message"+i, 0, (int)res.get(i));
            Assert.assertFalse("Test Block User message"+i,
                    CA.getChatroom(roomId).getMessageMap().containsKey(res.get(i)));
        }
    }

    /**
     * Test 3: test direct message.
     */
    @Test
    public void testDirectMsg() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");

        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            data.add("Direct Message " + i);
        }
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            res.add(CA.sendMessage("direct", data.get(i), user1, -1, user2));
            SystemSleep();
        }

        List<AMessage> directMsg1 = MessageService.make().getDirectMessage(user1);
        List<AMessage> directMsg2 = MessageService.make().getDirectMessage(user2);
        for (int i = 0; i <= 5; i++) {
            Assert.assertFalse("Test Direct message without chatroom"+i,
                    CA.getChatroom(-1).getMessageMap().containsKey(res.get(i)));
            Assert.assertEquals("Test Direct message text"+i, data.get(i),
                    ((DirectMessage)directMsg1.get(i)).getText());
            Assert.assertEquals("Test Direct message text"+i, data.get(i),
                    ((DirectMessage)directMsg2.get(i)).getText());
            Assert.assertEquals("Test Direct message sender"+i, user1,
                    directMsg1.get(i).getSenderId());
            Assert.assertEquals("Test Direct message receiver"+i, user2,
                    directMsg1.get(i).getReceiverId());
        }
    }

    /**
     * Test 5: test Recall Message.
     */
    @Test
    public void testRecallMsg() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user3 = CA.signup("user3", "12345", 22, "Rice", "Eating", "avatar1.png");
        int roomId = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
        CA.addUser(roomId, user1);
        SystemSleep();
        CA.addUser(roomId, user2);
        SystemSleep();
        CA.addUser(roomId, user3);
        SystemSleep();

        //admin can recall message
        ArrayList<String> textData = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            textData.add("Broadcast Text Message from admin" + i);
        }
        ArrayList<Integer> res1 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            res1.add(CA.sendMessage("text", textData.get(i), user1, roomId, user1));
            SystemSleep();
        }

        Assert.assertTrue("Test Recall message",  ChatRoomStore.make().getRoom(roomId).getMessageMap().containsKey(res1.get(2)));
        Assert.assertTrue("Test Recall message Sender Status",  ChatRoomStore.make().getRoom(roomId).checkUser(user1));
        CA.operateMessage(roomId, user1, res1.get(2), "recall");
        Assert.assertFalse("Test Recall message",  ChatRoomStore.make().getRoom(roomId).getMessageMap().containsKey(res1.get(2)));
        Assert.assertTrue("Test Recall message Sender Status",  ChatRoomStore.make().getRoom(roomId).checkUser(user1));

        //non-admin can recall message
        ArrayList<String> textData2 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            textData2.add("Broadcast Text Message form user" + i);
        }

        ArrayList<Integer> res2 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            res2.add(CA.sendMessage("text", textData2.get(i), user2, roomId, user1));
            SystemSleep();
        }

        Assert.assertTrue("Test Recall message",  ChatRoomStore.make().getRoom(roomId).getMessageMap().containsKey(res2.get(2)));
        Assert.assertTrue("Test Recall message Sender Status",  ChatRoomStore.make().getRoom(roomId).checkUser(user2));
        CA.operateMessage(roomId, user2, res2.get(2), "recall");
        Assert.assertFalse("Test Recall message",  ChatRoomStore.make().getRoom(roomId).getMessageMap().containsKey(res2.get(2)));
        Assert.assertTrue("Test Recall message Sender Status",  ChatRoomStore.make().getRoom(roomId).checkUser(user2));
    }

    /**
     * Test 6: test Delete Message.
     */
    @Test
    public void testDeleteMsg() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user3 = CA.signup("user3", "12345", 22, "Rice", "Eating", "avatar1.png");
        int roomId = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
        CA.addUser(roomId, user1);
        SystemSleep();
        CA.addUser(roomId, user2);
        SystemSleep();
        CA.addUser(roomId, user3);
        SystemSleep();

        //admin can delete message
        ArrayList<String> textData = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            textData.add("Broadcast Text Message from admin" + i);
        }
        ArrayList<Integer> res1 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            res1.add(CA.sendMessage("text", textData.get(i), user1, roomId, user1));
            SystemSleep();
        }

        Assert.assertTrue("Test delete message",  ChatRoomStore.make().getRoom(roomId).getMessageMap().containsKey(res1.get(2)));
        Assert.assertTrue("Test delete message Sender Status",  ChatRoomStore.make().getRoom(roomId).checkUser(user1));
        CA.operateMessage(roomId, user1, res1.get(2), "delete");
        Assert.assertFalse("Test delete message",  ChatRoomStore.make().getRoom(roomId).getMessageMap().containsKey(res1.get(2)));
        Assert.assertTrue("Test delete message Sender Status",  ChatRoomStore.make().getRoom(roomId).checkUser(user1));

        //non-admin can not delete message
        ArrayList<String> textData2 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            textData2.add("Broadcast Text Message form user" + i);
        }

        ArrayList<Integer> res2 = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            res2.add(CA.sendMessage("text", textData2.get(i), user2, roomId, user1));
            SystemSleep();
        }

        Assert.assertTrue("Test delete message",  ChatRoomStore.make().getRoom(roomId).getMessageMap().containsKey(res2.get(2)));
        Assert.assertTrue("Test delete message Sender Status",  ChatRoomStore.make().getRoom(roomId).checkUser(user2));
        CA.operateMessage(roomId, user2, res2.get(2), "delete");
        Assert.assertTrue("Test delete message",  ChatRoomStore.make().getRoom(roomId).getMessageMap().containsKey(res2.get(2)));
        Assert.assertTrue("Test delete message Sender Status",  ChatRoomStore.make().getRoom(roomId).checkUser(user2));

    }




}