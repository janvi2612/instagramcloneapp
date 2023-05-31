package com.example.instagramclone.model

data class Post(var caption:String,val email:String,var img:String,var imgpost:String, var like:String,var location :String,var userid:String) {
constructor():this("","","","","","","")

}