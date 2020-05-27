package com.cookandroid.listmembers.models;

import java.util.HashMap;
import java.util.Map;

public class Message {

   public Map<String, Boolean> users = new HashMap<>();
//   메시지 유저
   public Map<String, Comment> comment = new HashMap<>();
//   메시지 내용

   public static class Comment{
       public String uid;
       public String messages;
       public String reason;
       public Object time;
   }
}

