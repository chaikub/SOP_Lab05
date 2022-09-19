package com.example.lab05;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word words = new Word();

    public WordPublisher() {

    }

    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> addBadWord(@PathVariable("word") String s){
        words.badWords.add(s);
        return  words.badWords;
    }

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        for(int i = 0; i < words.badWords.size(); i++){
            if(words.badWords.get(i).equals(s)){
                words.badWords.remove(i);
            }
        }
        return words.badWords;
    }

    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
        words.goodWords.add(s);
        return  words.goodWords;
    }

    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        for(int i = 0; i < words.goodWords.size(); i++){
            if(words.goodWords.get(i).equals(s)){
                words.goodWords.remove(i);
            }
        }
        return words.goodWords;
    }

    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.GET)
    public void proofSentence(@PathVariable("sentence") String s){
        String count = "";
        for(String i : words.goodWords){
            if(s.indexOf(i) !=-1){
                count+="g";
                break;
            }
        }
        for(String i : words.badWords){
            if(s.indexOf(i) !=-1){
                count+="b";
                break;
            }
        }
        if(count.equals("g")){
            rabbitTemplate.convertAndSend("DirectExchange", "good", s);
        } else if (count.equals("b")) {
            rabbitTemplate.convertAndSend("DirectExchange", "bad", s);
        } else if (count.equals("gb")) {
            rabbitTemplate.convertAndSend("FanoutExchange", "", s);
        }
    }
}
