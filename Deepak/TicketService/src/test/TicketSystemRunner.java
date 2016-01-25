package test;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import test.factory.TestTicketFactory;
import test.services.TicketOperationTestSuit;

/**
 * Created by root on 20/1/16.
 * test runner class to execute testSuite.
 */
public class TicketSystemRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TicketOperationTestSuit.class, TestTicketFactory.class);

        for(Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
