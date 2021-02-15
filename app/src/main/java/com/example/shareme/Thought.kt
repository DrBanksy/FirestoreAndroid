package com.example.shareme

data class Thought(val username:String, val timestamp: java.util.Date, val thoughtTxt:String,
                   val numLikes:Int, val numComments:Int, val documentId:String)
