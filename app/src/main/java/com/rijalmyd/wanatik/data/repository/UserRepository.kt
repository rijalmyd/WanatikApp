package com.rijalmyd.wanatik.data.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.rijalmyd.wanatik.data.model.History
import com.rijalmyd.wanatik.data.model.Motif
import com.rijalmyd.wanatik.data.model.Product
import com.rijalmyd.wanatik.data.model.SignInResult
import com.rijalmyd.wanatik.data.model.Store
import com.rijalmyd.wanatik.data.source.Result
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {
    fun signInGoogle(context: Context): Flow<Result<SignInResult>>
    suspend fun signOut()
    fun getSignedUser(): Flow<FirebaseUser?>
    fun getPopularProducts(): Flow<Result<List<Product>>>
    fun getAllProducts(): Flow<Result<List<Product>>>
    fun getProductsByStoreId(storeId: String): Flow<Result<List<Product>>>
    fun getNearStores(): Flow<Result<List<Store>>>
    fun classify(image: File, imageUri: Uri): Flow<Result<List<Motif?>>>
    fun getRelatedProducts(classDetected: String): Flow<Result<List<Product>>>
    suspend fun clickedProduct(productId: String)
    fun getDetailProduct(productId: String): Flow<Result<Product?>>
    fun saveToHistory(image: Uri, motifs: List<Motif?>): Flow<Result<String?>>
    fun getHistories(): Flow<Result<List<History>>>
    fun getDetailHistory(id: String): Flow<Result<History?>>
    fun getMotifById(id: String): Flow<Result<Motif?>>
    fun searchProducts(query: String): Flow<Result<List<Product>>>
}