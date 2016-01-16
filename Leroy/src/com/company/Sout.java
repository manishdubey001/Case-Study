package com.company;

/**
 * Created by root on 14/1/16.
 */
public class Sout {
    static final String ACT_CHOOSE = "\n\nEnter an appropriate command:";
    static final String ACT_CREATE_TICKET = "\n---------------------------------------\n 1 - Create Ticket initialized\n------------------------------";
    static final String ACT_NOT_FOUND = "Ticket/Tickets not found in the system";
    static final String ACT_THANKYOU = "THANK YOU..!!:";
    static final String ACT_INVALID = "Invalid Input: ";
    static final String ACT_ERROR = "IO Error: ";
    static final String ACT_TSUBJECT = "Enter a Subject: ";
    static final String ACT_TAGENTNAME = "Enter an Agent Name: ";
    static final String ACT_TTAGS = "Enter a TagName (use ',' for multiple TagsNames)";
    static final String ACT_TTAGS_SINGLE = "Enter a Tag name";
    static final String ACT_TIDUPDATE = "Please enter a Ticket Id to update: ";
    static final String ACT_CHOOSE_TAG_AGENT = "\n---------------------------------------\n a - Change Agent name\n b - Change Tag name\n--------------------------------";
    static final String ACT_TABLE_HEADER = "  ID  |  AGENT  |  SUBJECT  |  TAGS  |  CREATED  |  MODIFIED  ";
    static final String ACT_TID = "Enter a Ticket Id: ";
    static final String ACT_ARE_YOU_SURE = "Are you sure: ";
    static final String ACT_YES_OR_NO = "1 - Yes\n0 - No";
    static final String ACT_TICKETS_IN_SYSTEM = "Following are the Tickets in the System";
    static final String ACT_REMOVE_SUCCESS = "Removed Successfully ";

    static String getAllCommands(){
        System.out.println("Please choose an appropriate input.");
        System.out.println("1 - Create Ticket.");
        System.out.println("2 - Update Ticket by Id.");
        System.out.println("3 - Delete/Remove Ticket by Id.");
        System.out.println("4 - Select single Ticket by Id.");
        System.out.println("5 - Select all Tickets.");
        System.out.println("6 - Select Tickets assigned to specific Agent.");
        System.out.println("7 - Ticket count grouped by Agent name (order by Agent name).");
        System.out.println("8 - Select all Tickets by specific Tags.");
        System.out.println("9 - Quit.");
        return "\n";
    }
}
