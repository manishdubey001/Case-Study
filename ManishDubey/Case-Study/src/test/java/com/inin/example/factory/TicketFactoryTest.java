package com.inin.example.factory;

import com.inin.example.model.Ticket;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactoryTest {

    @Test
    public void testNewInstance()
    {
        HashSet<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        Ticket ticket = TicketFactory.newInstance("Test Subject","Agent1",tags);
        Assert.assertEquals("Test Subject",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgentName());
        Assert.assertEquals(tags,ticket.getTags());
    }
}
