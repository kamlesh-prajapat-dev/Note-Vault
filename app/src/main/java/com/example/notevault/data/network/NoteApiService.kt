package com.example.notevault.data.network

import com.example.notevault.data.UserPreferencesRepository
import com.example.notevault.data.model.note.NotesEntry
import com.example.notevault.data.model.serializers.LocalDateTimeSerializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL = "Your API"

private val module = SerializersModule {
    contextual(LocalDateTimeSerializer)
}

private val json = Json {
    serializersModule = module
    ignoreUnknownKeys = true
}

interface NoteApiService {

    @GET("notes")
    suspend fun getNotes(): Response<List<NotesEntry>>

    @POST("notes")
    suspend fun saveNote(@Body noteEntry: NotesEntry): Response<NotesEntry>

    @DELETE("notes")
    suspend fun deleteNoteById(@Body id: String): Response<Unit>

    @PUT("notes/id/{myId}")
    suspend fun updateNote(@Path("myId") id: String, @Body noteEntry: NotesEntry) : Response<NotesEntry>
}

object NoteApi {
    fun create(userPreferencesRepository: UserPreferencesRepository): NoteApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userPreferencesRepository))
            .build()

        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(NoteApiService::class.java)
    }
}