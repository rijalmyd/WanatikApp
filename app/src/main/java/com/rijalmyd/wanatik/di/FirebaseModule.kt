package com.rijalmyd.wanatik.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.rijalmyd.wanatik.data.source.firebase.FirebaseService
import com.rijalmyd.wanatik.data.source.firebase.FirebaseServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    @Singleton
    abstract fun provideFirebaseService(firebaseServiceImpl: FirebaseServiceImpl): FirebaseService

    companion object {
        @Provides
        @Singleton
        fun provideSignInClient(@ApplicationContext context: Context): SignInClient =
            Identity.getSignInClient(context)

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth =
            Firebase.auth

        @Provides
        @Singleton
        fun provideFirebaseFirestore(): FirebaseFirestore =
            Firebase.firestore

        @Provides
        @Singleton
        fun provideFirebaseStorage(): FirebaseStorage =
            Firebase.storage

        @Provides
        @Singleton
        fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager =
            CredentialManager.create(context)

    }
}