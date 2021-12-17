package edu.rice.comp504.adapter;

import edu.rice.comp504.model.message.*;
import edu.rice.comp504.model.store.MessageService;
import org.junit.Assert;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InvitationTest {

    /**
     * Make the system sleep for 0.001 sec.
     */
    public void SystemSleep() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            System.out.println("the system don't want to sleep");
        }
    }

//    /**
//     * Test 1: test to invite a user to a chatroom.
//     */
//    @Test
//    public void testInvitation() {
//        ChatRoomAdapter CA = new ChatRoomAdapter();
//        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
//        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");
//        int user3 = CA.signup("user3", "12345", 22, "Rice", "Eating", "avatar1.png");
//        int roomId = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
//        CA.addUser(roomId, user1);
//        SystemSleep();
//        CA.addUser(roomId, user2);
//        SystemSleep();
//        int res1 = CA.inviteJoinChatroom(user2, user3, roomId);
//        Assert.assertEquals("Test no-admin invite operation", 0, res1);
//        int res2 = CA.inviteJoinChatroom(user1, user3, roomId);
//        AMessage msg = MessageService.make().getMsg(res2);
//        Assert.assertEquals("Test invitation sender", user1, ((InvitationMessage)msg).getSenderId());
//        Assert.assertEquals("Test invitation receiver", user3, ((InvitationMessage)msg).getReceiverId());
//        Assert.assertEquals("Test invitation roomId", roomId, ((InvitationMessage)msg).getRoomId())
//
//    }


}
