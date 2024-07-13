package com.rijalmyd.wanatik.data.source.firebase

import android.content.Context
import android.net.Uri
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.rijalmyd.wanatik.R
import com.rijalmyd.wanatik.data.model.History
import com.rijalmyd.wanatik.data.model.Motif
import com.rijalmyd.wanatik.data.model.SignInResult
import com.rijalmyd.wanatik.data.model.Store
import com.rijalmyd.wanatik.data.source.firebase.model.ProductResponse
import com.rijalmyd.wanatik.util.getCurrentDateTimeFormatted
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseServiceImpl @Inject constructor(
    private val credentialManager: CredentialManager,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : FirebaseService {

    override suspend fun signInGoogle(context: Context): SignInResult {
        val googleIdOptions = GetGoogleIdOption.Builder()
            .setAutoSelectEnabled(false)
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptions)
            .build()
        val result = credentialManager.getCredential(
            context, request
        )
        val credential = result.credential
        val googleIdTokenCredential = GoogleIdTokenCredential
            .createFrom(credential.data)
        val googleIdToken = googleIdTokenCredential.idToken
        val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        val signInTask = auth.signInWithCredential(firebaseCredential).await()
        val user = signInTask.user

        return SignInResult(
            user = user,
            errorMessage = null
        )
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun getSignedUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun getPopularProducts(): List<ProductResponse> {
        return firestore.collection("products")
            .orderBy("clicked", Query.Direction.DESCENDING)
            .limit(5)
            .get()
            .await()
            .toObjects()
    }

    override suspend fun getAllProducts(): List<ProductResponse> {
        return firestore.collection("products")
            .orderBy("clicked", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects()
    }

    override suspend fun getProductsByStoreId(storeId: String): List<ProductResponse> {
        return firestore.collection("products")
            .whereEqualTo("storeId", storeId)
            .get()
            .await()
            .toObjects()
    }

    override suspend fun getRelatedProducts(motifName: String): List<ProductResponse> {
        return firestore.collection("products")
            .whereEqualTo("motifName", motifName)
            .get()
            .await()
            .toObjects()
    }

    override suspend fun clickedProduct(productId: String) {
        firestore.collection("products")
            .document(productId)
            .update("clicked", FieldValue.increment(1))
            .await()
    }

    override suspend fun getNearStores(): List<Store> {
        return firestore.collection("stores")
            .get()
            .await()
            .toObjects()
    }

    override suspend fun getStoreById(storeId: String): Store? {
        return firestore.collection("stores")
            .document(storeId)
            .get()
            .await()
            .toObject()
    }

    override suspend fun getMotifById(motifId: String): Motif? {
        return firestore.collection("motifs")
            .document(motifId)
            .get()
            .await()
            .toObject()
    }

    override suspend fun getDetailProduct(productId: String): ProductResponse? {
        return firestore.collection("products")
            .document(productId)
            .get()
            .await()
            .toObject()
    }

    override suspend fun saveToHistory(image: Uri, motifs: List<Motif?>) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val storageRef = storage.reference.child("history/${Date().time}.jpg")
            val uploadTask = storageRef.putFile(image).await()
            val downloadUrl = uploadTask.metadata?.reference?.downloadUrl?.await()

            if (downloadUrl != null) {
                firestore.collection("histories")
                    .document()
                    .set(
                        History(
                            id = firestore.collection("histories").document().id,
                            imageUrl = downloadUrl.toString(),
                            motifs = motifs,
                            userId = userId,
                            date = getCurrentDateTimeFormatted()
                        )
                    )
                    .await()
            } else {
                throw Exception("Error downloading url")
            }
        }
    }

    override suspend fun getHistories(): List<History> {
        return firestore.collection("histories")
            .whereEqualTo("userId", auth.currentUser?.uid)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects()
    }

    override suspend fun getDetailHistory(id: String): History? {
        return firestore.collection("histories")
            .whereEqualTo("id", id)
            .limit(1)
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.toObject()
    }

    override suspend fun searchProducts(query: String): List<ProductResponse> {
        return firestore.collection("products")
            .whereGreaterThanOrEqualTo("title", query)
            .get()
            .await()
            .toObjects()
    }
}