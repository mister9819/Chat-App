package edu.rice.comp504.model.store;


import edu.rice.comp504.adapter.ChatRoomAdapter;
import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.chatroom.NullChatRoom;
import edu.rice.comp504.model.chatroom.PublicChatRoom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChatroomStoreTest {
    ChatRoomAdapter CA;

    @Before
    public void before() {
        CA = new ChatRoomAdapter();
    }

    /**
     * Test 1: test createChatRoom.
     */
    @Test
    public void testCreateChatRoom() {
        int cnt = ChatRoomStore.chatroomCount;
        AChatRoom room = ChatRoomStore.make().createChatRoom(UserStore.make().getUser(1), 10, "test chatroom", "test", "public", "");
        Assert.assertEquals(ChatRoomStore.chatroomCount, cnt + 1);
        int roomId = room.getId();
        Assert.assertEquals(ChatRoomStore.make().getRoom(roomId), room);
    }

    /**
     * Test 2: test deleteRoom.
     */
    @Test
    public void testDeleteRoom() throws InterruptedException {
        int cnt = ChatRoomStore.make().chatRoomMap.size();
        AChatRoom room = ChatRoomStore.make().createChatRoom(UserStore.make().getUser(1), 10, "test chatroom", "test", "public", "");
        Assert.assertEquals(ChatRoomStore.make().chatRoomMap.size(), cnt + 1);
        int roomId = room.getId();
        ChatRoomStore.make().deleteRoom(roomId);
        Assert.assertEquals(ChatRoomStore.make().chatRoomMap.size(), cnt);
        ChatRoomStore.make().deleteRoom(roomId);
        Assert.assertEquals(ChatRoomStore.make().chatRoomMap.size(), cnt);
    }

    /**
     * Test 2: test deleteRoomCheck.
     */
    @Test
    public void testDeleteRoomCheck() {
        AChatRoom room = ChatRoomStore.make().createChatRoom(UserStore.make().getUser(1), 10, "test chatroom", "test", "public", "");
        int roomId = room.getId();
        Assert.assertEquals(ChatRoomStore.make().deleteRoomCheck(roomId, 1), "success");
        Assert.assertEquals(ChatRoomStore.make().deleteRoomCheck(ChatRoomStore.chatroomCount, 1), "wrong room id");
        Assert.assertEquals(ChatRoomStore.make().deleteRoomCheck(roomId, 2), "no permission");
    }

    /**
     * Test 3: test getRoom.
     */
    @Test
    public void testGetRoom() {
        Assert.assertEquals(ChatRoomStore.make().getRoom(0).getClass(), PublicChatRoom.class);
        Assert.assertNull(ChatRoomStore.make().getRoom(20));
        Assert.assertEquals(ChatRoomStore.make().getRoom(-1).getClass(), NullChatRoom.class);
    }

    /**
     * Test 4: test getUserRoom.
     */
    @Test
    public void testGetUserRoom() {
        int userId = UserStore.make().signup("d", "d", 18, "rice", "none", "avatar1.png");
        AChatRoom room1 = ChatRoomStore.make().createChatRoom(UserStore.make().getUser(userId), 10, "test chatroom", "test", "public", "");
        AChatRoom room2 = ChatRoomStore.make().createChatRoom(UserStore.make().getUser(userId), 10, "test chatroom", "test", "public", "");
        Assert.assertEquals(ChatRoomStore.make().getUserRoom(userId).size(), 2);
        Assert.assertEquals(ChatRoomStore.make().getUserRoom(userId).get(0), room1);
        Assert.assertEquals(ChatRoomStore.make().getUserRoom(userId).get(1), room2);
    }

    /**
     * Test 5: test getJoinRoom.
     */
    @Test
    public void testGetJoinRoom() {
        int userId = UserStore.make().signup("d", "d", 18, "rice", "none", "avatar1.png");
//        Assert.assertEquals(ChatRoomStore.make().getJoinRoom(userId).size(), 11);
        Assert.assertEquals(ChatRoomStore.make().getJoinRoom(userId).get(0), ChatRoomStore.make().getRoom(0));
        Assert.assertEquals(ChatRoomStore.make().getJoinRoom(userId).get(1), ChatRoomStore.make().getRoom(1));
    }

    /**
     * Test 6: test checkRoom.
     */
    @Test
    public void testCheckRoom() {
        Assert.assertTrue(ChatRoomStore.make().checkRoom(0));
        ChatRoomStore.make().deleteRoom(0);
        Assert.assertFalse(ChatRoomStore.make().checkRoom(0));
    }
}
