package org.example;

public class Main {
    public static AgentFrame agentFrame;
    public static Frame apartamentFrame;

    public static void main(String[] args) {

         agentFrame = new AgentFrame();
         apartamentFrame = new Frame(agentFrame);
        apartamentFrame.setVisible(true);
    }
}