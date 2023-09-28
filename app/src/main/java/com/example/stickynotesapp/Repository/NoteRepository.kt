package com.example.stickynotesapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stickynotesapp.Models.NoteRequest
import com.example.stickynotesapp.Models.NoteResponse
import com.example.stickynotesapp.Models.UserResponse
import com.example.stickynotesapp.Retrofit.NoteAPI
import com.example.stickynotesapp.Utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteAPI: NoteAPI) {

    private val _noteLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val noteLiveData : LiveData<NetworkResult<List<NoteResponse>>>
    get() = _noteLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData : LiveData<NetworkResult<String>>
    get() = _statusLiveData


    suspend fun getNotes(){
        _noteLiveData.postValue(NetworkResult.Loading())
        val response = noteAPI.getNote()

        if (response.isSuccessful && response.body() != null) {
            _noteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObject = JSONObject(response.errorBody()!!.charStream().readText())
            _noteLiveData.postValue(NetworkResult.Error(errorObject.getString("message")))
        } else {
            _noteLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun createNote( noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteAPI.createNote(noteRequest)
        handleResponse(response, "Note Created")
    }


    suspend fun updateNote( noteId: String, noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteAPI.updateNote(noteId, noteRequest)
        handleResponse(response, "Note Updated")
    }

    suspend fun deleteNote( noteId: String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteAPI.deleteNote(noteId)
        handleResponse(response, "Note Deleted")
    }

    private fun handleResponse(response: Response<NoteResponse>, message : String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went Wrong"))
        }
    }

}