package com.example.lab05;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SentenceConsumer {
    protected Sentence sentences = new Sentence();


    @RabbitListener(queues = "BadWord")
    public void addBadSentence(String s){
        sentences.badSentences.add(s);
        System.out.println("In addBadSentence Method : " + sentences.badSentences);
    }

    @RabbitListener(queues = "GoodWord")
    public void addGoodSentence(String s){
        sentences.goodSentences.add(s);
        System.out.println("In addGoodSentence Method : " + sentences.goodSentences);
    }
}
