package com.example.stickynotesapp.Retrofit

import com.example.stickynotesapp.Models.NoteRequest
import com.example.stickynotesapp.Models.NoteResponse
import retrofit2.Response
import retrofit2.http.*

interface NoteAPI {

    @GET("/note")
    suspend fun getNote() : Response<List<NoteResponse>>

    @POST("/note")
    suspend fun createNote(@Body noteRequest: NoteRequest) : Response<NoteResponse>

    @PUT("/note/{noteId}")
    suspend fun updateNote( @Path("noteId") noteId:String, @Body noteRequest: NoteRequest) : Response<NoteResponse>

    @DELETE("/note/{noteId}")
    suspend fun  deleteNote(@Path("noteId") noteId: String): Response<NoteResponse>
}