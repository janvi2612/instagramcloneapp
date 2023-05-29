package com.example.instagramclone.model

data class User(var name : String,var email: String,var number : String,var username: String,var profilepic : String,var id : String){
    constructor(): this("","","","","","")
}
