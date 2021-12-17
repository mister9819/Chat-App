package edu.rice.comp504.model.factory;


import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.chatroom.PrivateChatRoom;
import edu.rice.comp504.model.chatroom.PublicChatRoom;
import edu.rice.comp504.model.store.UserStore;
import org.junit.Assert;
import org.junit.Test;

public class ChatRoomFactoryTest {

    /**
     * Test 1: test make.
     */
    @Test
    public void testMake() {
        ChatRoomFactory fac1 = ChatRoomFactory.make();
        ChatRoomFactory fac2 = ChatRoomFactory.make();
        Assert.assertEquals(fac1, fac2);
    }

    /**
     * Test 2: test make chatroom.
     */
    @Test
    public void testMakeChatroom() {
        int userId = UserStore.make().signup("d", "d", 18, "rice", "none", "avatar1.png");
        AChatRoom room1 = ChatRoomFactory.make().make(0, UserStore.make().getUser(userId), 10, "test chatroom", "test", "public", "");
        Assert.assertEquals(room1.getClass(), PublicChatRoom.class);
        AChatRoom room2 = ChatRoomFactory.make().make(0, UserStore.make().getUser(userId), 10, "test chatroom", "test", "private", "1");
        Assert.assertEquals(room2.getClass(), PrivateChatRoom.class);

    }
}
