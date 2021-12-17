package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.adapter.ChatRoomAdapter;
import edu.rice.comp504.model.store.ChatRoomStore;
import org.junit.Assert;
import org.junit.Test;

public class ChatroomFunctionTest {

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

    /**
     * Test 1: test public chatroom basic functions.
     */
    @Test
    public void testPublicChatroom() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user3 = CA.signup("user3", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user4 = CA.signup("user4", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user5 = CA.signup("user5", "12345", 22, "Rice", "Eating", "avatar1.png");
        int roomId = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
        Assert.assertEquals("Test Public Room Type", "public", CA.getChatroom(roomId).getType());
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

        //member test
        Assert.assertTrue(CA.checkUser(roomId, user1));
        Assert.assertTrue(CA.checkUser(roomId, user2));
        Assert.assertTrue(CA.checkUser(roomId, user3));
        Assert.assertTrue(CA.checkUser(roomId, user4));
        Assert.assertTrue(CA.checkUser(roomId, user5));

        //admin test
        Assert.assertTrue(CA.getChatroom(roomId).isAdmin(user1));
        Assert.assertFalse(CA.getChatroom(roomId).isAdmin(user2));
        CA.addAdmin(roomId, user2);
        CA.addAdmin(roomId, user2); //Duplicated operation test
        Assert.assertTrue(CA.getChatroom(roomId).isAdmin(user1));

        //block test

        String res2 = CA.blockUser(roomId, user4);
        CA.blockUser(roomId, user4); //Duplicated operation test
        Assert.assertEquals("Test admin block operation", "success", res2);
        Assert.assertEquals("Test admin block operation", "success", res2);
        Assert.assertEquals(CA.blockUser(roomId, 100), "not in the room");

        //unblock test

        String res4 = CA.unblockUser(roomId, user4);
        CA.unblockUser(roomId, user4); //Duplicated operation test
        Assert.assertEquals("Test admin block operation", "success", res4);
        Assert.assertFalse(CA.getChatroom(roomId).checkBlock(user4));

        //leave room test
        String res5 = CA.leaveRoom(roomId, user5);
        CA.leaveRoom(roomId, user5); //Duplicated operation test
        Assert.assertEquals("Test user leave chatroom", "success", res5);
        Assert.assertFalse(CA.checkUser(roomId, user5));

        //close room test

        String res7 = CA.closeRoom(roomId, user1);
        Assert.assertEquals("Test admin close chatroom", "success", res7);
        Assert.assertFalse(ChatRoomStore.make().checkRoom(roomId));

        //other functions test
        int roomId2 = CA.createRoom(user1, 10, "test chatroom2", "test chatroom2", "public", "12345");
        AChatRoom room = CA.getChatroom(roomId2);
        room.setName("new name");
        Assert.assertEquals("Test chatroom set name", "new name", room.getName());
        room.setSize(2);
        Assert.assertEquals("Test chatroom set size", 2, room.getSize());
        room.setDescription("new description");
        Assert.assertEquals("Test chatroom set description", "new description", room.getDescription());

        String res8 = CA.addUser(roomId2, user2);
        SystemSleep();
        String res9 = CA.addUser(roomId2, user3);
        SystemSleep();
        Assert.assertEquals("Test add user before the room is full", "success", res8);
        Assert.assertEquals("Test add user after the room is full", "Room is full", res9);

    }

    /**
     * Test 2: test private chatroom password setting.
     * Other functions are the same with public chatroom.
     */
    @Test
    public void testPrivateChatroom() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user3 = CA.signup("user3", "12345", 22, "Rice", "Eating", "avatar1.png");
        int roomId = CA.createRoom(user1, 10, "test private chatroom", "test private chatroom", "private", "12345");
        Assert.assertEquals("Test Private Room Type", "private", CA.getChatroom(roomId).getType());
        CA.addUser(roomId, user1);
        SystemSleep();
        CA.addUser(roomId, user2);
        SystemSleep();
        CA.addUser(roomId, user3);
        SystemSleep();

        Assert.assertEquals("Test Private Chatroom Original PassHash", "12345", ((PrivateChatRoom) CA.getChatroom(roomId)).getPassHash());
        String passHash = "hate speech";
        ((PrivateChatRoom) CA.getChatroom(roomId)).setPassHash(passHash);
        Assert.assertEquals("Test Private Chatroom Original PassHash", passHash, ((PrivateChatRoom) CA.getChatroom(roomId)).getPassHash());

    }


    /**
     * Test 3: test NullChatRoom.
     */
    @Test
    public void testNullChatRoom() {
        NullChatRoom make = NullChatRoom.make();
        Assert.assertEquals(make.getId(), -1);
        Assert.assertEquals(make.getSize(), 0);
        Assert.assertEquals(make.getName(), "NullChatRoom");
    }

    /**
     * Test 3: leave all chatroom test.
     */
    @Test
    public void testLeaveAllRoom() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int user1 = CA.signup("user1", "12345", 22, "Rice", "Eating", "avatar1.png");
        int user2 = CA.signup("user2", "12345", 22, "Rice", "Eating", "avatar1.png");
        int roomId1 = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
        int roomId2 = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
        int roomId3 = CA.createRoom(user1, 10, "test chatroom", "test chatroom", "public", "12345");
        CA.addUser(roomId1, user1);
        SystemSleep();
        CA.addUser(roomId1, user2);
        SystemSleep();
        CA.addUser(roomId2, user1);
        SystemSleep();
        CA.addUser(roomId2, user2);
        SystemSleep();
        CA.addUser(roomId3, user1);
        SystemSleep();
        CA.addUser(roomId3, user2);
        SystemSleep();

        Assert.assertEquals("Test LeaveAllRoom", 3, CA.getUserChatRoom(user2).size());
        Assert.assertEquals("Test LeaveAllRoom", 2, ChatRoomStore.make().getRoom(roomId1).getAll().size());
        Assert.assertEquals("Test LeaveAllRoom", 2, ChatRoomStore.make().getRoom(roomId2).getAll().size());
        Assert.assertEquals("Test LeaveAllRoom", 2, ChatRoomStore.make().getRoom(roomId3).getAll().size());
        CA.leaveAllRoom(user2);
        Assert.assertEquals("Test LeaveAllRoom", 0, CA.getUserChatRoom(user2).size());
        Assert.assertEquals("Test LeaveAllRoom", 1, ChatRoomStore.make().getRoom(roomId1).getAll().size());
        Assert.assertEquals("Test LeaveAllRoom", 1, ChatRoomStore.make().getRoom(roomId2).getAll().size());
        Assert.assertEquals("Test LeaveAllRoom", 1, ChatRoomStore.make().getRoom(roomId3).getAll().size());
    }

}