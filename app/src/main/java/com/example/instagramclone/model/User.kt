package com.example.instagramclone.model

data class User(var name : String,var email: String,var number : String,var username: String,var profilepic : String,var id : String, var bio : String,var followersno :String, var followingno : String,var postno :String){
    constructor(): this("","","","","","","","","","")
}
