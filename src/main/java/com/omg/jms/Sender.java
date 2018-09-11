package com.omg.jms;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Auther: cui
 * @Date: 2018-09-09 20:45
 * @Description:
 */
public class Sender {
    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
        try {
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("my-queue");
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("this is a queue message");
            producer.send(textMessage);

            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void test(){
        System.out.printf("+++++++++++++++++++++++");
    }
}
