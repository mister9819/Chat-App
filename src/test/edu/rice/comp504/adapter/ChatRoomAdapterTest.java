package edu.rice.comp504.adapter;

import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.store.ChatAppStore;
import edu.rice.comp504.model.store.ChatRoomStore;
import edu.rice.comp504.model.store.MessageService;
import edu.rice.comp504.model.store.UserStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChatRoomAdapterTest {
    ChatRoomAdapter ca;

    @Before
    public void before() {
        ca = new ChatRoomAdapter();
    }

    /**
     * Test 1: test addUser.
     */
    @Test
    public void testAddUser() {
        int userId = UserStore.make().signup("d", "d", 18, "rice", "none", "avatar1.png");
        AChatRoom room1 = ChatRoomStore.make().createChatRoom(UserStore.make().getUser(userId), 10, "test chatroom", "test", "public", "");
        String resp1 = ca.addUser(room1.getId(), userId);
        Assert.assertEquals(resp1, "already in the room");
        Assert.assertEquals(room1.getAll().get(0), UserStore.make().getUser(userId));

        AChatRoom room2 = ChatRoomStore.make().createChatRoom(UserStore.make().getUser(1), 10, "test chatroom", "test", "public", "");
        String resp2 = ca.addUser(room2.getId(), userId);
        Assert.assertEquals(resp2, "success");
        Assert.assertEquals(room2.getAll().get(1), UserStore.make().getUser(userId));

    }

    /**
     * Test 2: test createChatRoom.
     */
    @Test
    public void testCreateChatRoom() {
        Assert.assertEquals(ChatRoomStore.chatroomCount, 9);
        int roomId = ca.createRoom(1, 10, "test chatroom", "test", "public", "");
        Assert.assertEquals(ChatRoomStore.chatroomCount, 10);
    }

    /**
     * Test 3: test removeUser.
     */
    @Test
    public void testCreateRemoveUser() {
        ca.removeUser(0, 1);
        Assert.assertFalse(ca.checkUser(0,1));
    }

    /**
     * Test 3: test addAdmin.
     */
    @Test
    public void testAddAdmin() {
        int userId = UserStore.make().signup("d", "d", 18, "rice", "none", "avatar1.png");
        ca.addUser(0, userId);
        Assert.assertEquals(ca.addAdmin(0, userId), "success");
        Assert.assertTrue(ChatRoomStore.make().getRoom(0).isAdmin(userId));
    }

    /**
     * Test 4: test block.
     */
    @Test
    public void testBlock() {
        int adminId = UserStore.make().signup("d", "d", 18, "rice", "none", "avatar1.png");
        int roomId = ca.createRoom(adminId, 10, "test chatroom", "test", "public", "");
        int user1Id = UserStore.make().signup("e", "e", 18, "rice", "none", "avatar1.png");
        int user2Id = UserStore.make().signup("f", "f", 18, "rice", "none", "avatar1.png");
        ca.addUser(roomId, user1Id);
        Assert.assertEquals(ca.blockUser(roomId, user1Id), "success");
        Assert.assertTrue(ChatRoomStore.make().getRoom(roomId).checkBlock(user1Id));
        Assert.assertEquals(ca.blockUser(roomId, user1Id), "already blocked");
        Assert.assertEquals(ca.unblockUser(roomId, user1Id), "success");
        Assert.assertFalse(ChatRoomStore.make().getRoom(roomId).checkBlock(user1Id));

        Assert.assertEquals(ca.blockUser(roomId, user2Id), "not in the room");
    }

    /**
     * Test 5: test leaveRoom.
     */
    @Test
    public void testLeaveRoom() {
        int adminId = UserStore.make().signup("d", "d", 18, "rice", "none", "avatar1.png");
        int roomId = ca.createRoom(adminId, 10, "test chatroom", "test", "public", "");
        int user1Id = UserStore.make().signup("e", "e", 18, "rice", "none", "avatar1.png");
        ca.addUser(roomId, user1Id);
        Assert.assertTrue(ca.checkUser(roomId, user1Id));
        Assert.assertEquals(ca.leaveRoom(roomId, user1Id), "success");
        Assert.assertFalse(ca.checkUser(roomId, user1Id));
    }

    /**
     * Test 6: test closeRoom.
     */
    @Test
    public void testCloseRoom() {
        int adminId = UserStore.make().signup("d", "d", 18, "rice", "none", "avatar1.png");
        int roomId = ca.createRoom(adminId, 10, "test chatroom", "test", "public", "");
        Assert.assertTrue(ChatRoomStore.make().checkRoom(roomId));
        Assert.assertEquals(ca.closeRoom(roomId, adminId), "success");
        Assert.assertFalse(ChatRoomStore.make().checkRoom(roomId));
    }

    /**
     * Test 7: test login.
     */
    @Test
    public void testLogin() {
        Assert.assertEquals(ca.login("x", "x"), -1);
    }

    /**
     * Test 8: test get methods.
     */
    @Test
    public void testGetMethods() {
        int adminId = ca.signup("d", "d", 18, "rice", "none", "avatar1.png");
        int roomId = ca.createRoom(adminId, 10, "test chatroom", "test", "public", "");
        Assert.assertEquals(ca.getUser(adminId), UserStore.make().getUser(adminId));
        Assert.assertEquals(ca.getRoomList(), ChatAppStore.make().getRoomList());
        Assert.assertEquals(ca.getChatroom(roomId), ChatRoomStore.make().getRoom(roomId));
        Assert.assertEquals(ca.getAllUser(), UserStore.make().getAllUser());
        Assert.assertEquals(ca.getUserList(roomId), ChatRoomStore.make().getRoom(roomId).getAll());
        Assert.assertEquals(ca.getJoinChatRoom(adminId), ChatRoomStore.make().getJoinRoom(adminId));
        Assert.assertEquals(ca.getUserChatRoom(adminId), ChatRoomStore.make().getUserRoom(adminId));
        Assert.assertEquals(ca.getUserMessage(adminId, roomId), ChatRoomStore.make().getRoom(roomId).getUserMessage(adminId));
        Assert.assertEquals(ca.getDirectMessage(adminId), MessageService.make().getDirectMessage(adminId));
    }
}
