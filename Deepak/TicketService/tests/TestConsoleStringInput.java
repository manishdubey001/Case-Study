import com.services.UserConsoleInput;
import com.services.UserInputException;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by root on 18/1/16.
 */
public class TestConsoleStringInput {
    static String data;
    /*
    * get the input from system */
    @Before
    public void justStringInput(){
        data = "JustText\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }


/*    @After
    public void resetInput(){
        try {
            System.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

/*    @Test
    public void testAcceptNumber(){
        String data = "1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Assert.assertEquals(1, UserConsoleInput.acceptNumber());
    }*/


    @Test
    public void testAcceptString() throws UserInputException {
        Assert.assertEquals("JustText\n", UserConsoleInput.acceptString());
    }


    @Test
    public void testGetMessage(){

        Assert.assertEquals("JustText\n", UserConsoleInput.getSubject());
       // System.out.println(UserConsoleInput.getSubject());
    }

/*
    @Test
    public void testGetAgentName(){
        Assert.assertEquals("JustText", UserConsoleInput.getAgentName());
    }*/


/*    @Test
    public void testGetTagName(){
        String tags = "Tag1, Tag2,    Tag3,   Tag4";
        System.setIn(new ByteArrayInputStream(tags.getBytes()));
        Set objSet = UserConsoleInput.getTagNames();
        Assert.assertNotNull(objSet);

        Assert.assertTrue(objSet instanceof HashSet);

        Set<String> tagSet = new HashSet<String>();
        tagSet.add("Tag1");
        tagSet.add("Tag2");
        tagSet.add("Tag3");
        tagSet.add("Tag4");

        Assert.assertEquals(tagSet,objSet);

    }*/


}
