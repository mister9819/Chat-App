package edu.rice.comp504.adapter;


import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.message.ImageMessage;
import edu.rice.comp504.model.message.LinkMessage;
import edu.rice.comp504.model.message.TextMessage;
import edu.rice.comp504.model.store.MessageService;
import org.junit.Assert;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LoadMsgHistoryTest {

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
     * Test 1: test to load message history from a chatroom.
     * Note: a user can only get the messages sent after his join.
     */
    @Test
    public void testLoadMsgHistory() {
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

        //send some messages before user5 joins
        ArrayList<String> data1 = new ArrayList<>();
        ArrayList<Integer> res1 = new ArrayList<>();
        data1.add("Text message 1 from user1");
        data1.add("Text message 2 from user2");
        data1.add("Image message 1 URL from user3");
        data1.add("Link message 2 URL from user4");
        res1.add(CA.sendMessage("text", data1.get(0), user1, roomId, user1));
        SystemSleep();
        res1.add(CA.sendMessage("text", data1.get(1), user2, roomId, user2));
        SystemSleep();
        res1.add(CA.sendMessage("image", data1.get(2), user3, roomId, user3));
        SystemSleep();
        res1.add(CA.sendMessage("link", data1.get(3), user4, roomId, user4));
        SystemSleep();

        //user5 joins
        CA.addUser(roomId, user5);
        SystemSleep();
        //send some messages after user5 joins
        ArrayList<String> data2 = new ArrayList<>();
        ArrayList<Integer> res2 = new ArrayList<>();
        data2.add("Text message 3 from user1");
        data2.add("Text message 4 from user2");
        data2.add("Image message 3 URL from user3");
        data2.add("Image message 4 URL from user4");
        data2.add("Link message 5 URL from user5");
        res2.add(CA.sendMessage("text", data2.get(0), user1, roomId, user1));
        SystemSleep();
        res2.add(CA.sendMessage("text", data2.get(1), user2, roomId, user2));
        SystemSleep();
        res2.add(CA.sendMessage("image", data2.get(2), user3, roomId, user3));
        SystemSleep();
        res2.add(CA.sendMessage("image", data2.get(3), user4, roomId, user4));
        SystemSleep();
        res2.add(CA.sendMessage("link", data2.get(4), user5, roomId, user5));
        SystemSleep();

        List<AMessage> msgHistory = CA.getUserMessage(user5, roomId);
        //System.out.println(msgHistory.size());

        //user5 should not get the messages sent before his join
        for (int i = 0; i <= 3; i++) {
            Assert.assertFalse("Test load message before join"+i, msgHistory.contains(MessageService.make().getMsg(res1.get(i))));
        }


        //user5 can only get the messages sent after his join
        for (int i = 0; i <= 1; i++) {
            Assert.assertEquals("Test load message"+i, data2.get(i),
                    ((TextMessage)msgHistory.get(i)).getText());
        }
        for (int i = 2; i <= 3; i++) {
            Assert.assertEquals("Test load message"+i, data2.get(i),
                    ((ImageMessage)msgHistory.get(i)).getText());
        }
        Assert.assertEquals("Test load message"+4, data2.get(4),
                ((LinkMessage)msgHistory.get(4)).getText());
    }

}