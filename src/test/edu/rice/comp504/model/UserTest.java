package edu.rice.comp504.model;

import edu.rice.comp504.adapter.ChatRoomAdapter;
import edu.rice.comp504.model.store.UserStore;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * Test 1: test general user functions.
     */
    @Test
    public void testUserSetting() {
        ChatRoomAdapter CA = new ChatRoomAdapter();
        int userId = CA.signup("boy", "12345", 22, "Rice", "Eating", "avatar1.png");
        User user = CA.getUser(userId);

        user.setAge(18);
        Assert.assertEquals("Test set age", 18, user.getAge());
        user.setUsername("girl");
        Assert.assertEquals("Test set username", "girl", user.getUsername());
        user.setPassword("54321");
        Assert.assertEquals("Test set password", "54321", user.getPassword());
        user.setSchool("No school");
        Assert.assertEquals("Test set school", "No school", user.getSchool());
        user.setInterest("Exercising");
        Assert.assertEquals("Test set interest", "Exercising", user.getInterest());

    }

//    /**
//     * Test 2: test user login.
//     */
//    @Test
//    public void testUserLogin() {
//        ChatRoomAdapter CA = new ChatRoomAdapter();
//        int userId = CA.signup("boy", "12345", 22, "Rice", "Eating", "avatar1.png");
//        User user = CA.getUser(userId);
//
//        Assert.assertTrue(UserStore.make().getAllUser().contains(user));
//        CA.logout(userId);
//        Assert.assertFalse(UserStore.make().getAllUser().contains(user));
//        System.out.println( CA.login("boy", "12345") );
//        System.out.println(UserStore.make().getAllUser().contains(user));
//    }

}