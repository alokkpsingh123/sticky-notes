package com.example.stickynotesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stickynotesapp.Models.NoteRequest
import com.example.stickynotesapp.Repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    val statusLiveData get() = repository.statusLiveData
    val noteLiveData get() = repository.noteLiveData


    fun getNote() {
        viewModelScope.launch {
            repository.getNotes()
        }
    }

    fun createNote(noteRequest: NoteRequest) {
        viewModelScope.launch {
            repository.createNote(noteRequest)
        }
    }

    fun updateNote(noteId: String, noteRequest: NoteRequest) {
        viewModelScope.launch {
            repository.updateNote(noteId, noteRequest)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }
}