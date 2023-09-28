package com.example.stickynotesapp

import android.content.pm.PackageItemInfo
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stickynotesapp.Models.UserRequest
import com.example.stickynotesapp.Models.UserResponse
import com.example.stickynotesapp.Repository.UserRepository
import com.example.stickynotesapp.Utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(){

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
    get() = repository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
       viewModelScope.launch {
           repository.registerUser(userRequest)
       }
    }

    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            repository.loginUser(userRequest)
        }
    }

    fun validateCredentials( username: String, email: String, password: String, isLogin: Boolean) : Pair<Boolean, String>{
        var result = Pair(true, "")
        if( (!isLogin && TextUtils.isEmpty(username)) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide credentials");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false, "Please provide valid email")
        }else if(password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        return  result
    }
}