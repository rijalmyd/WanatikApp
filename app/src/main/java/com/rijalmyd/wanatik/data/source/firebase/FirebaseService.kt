package com.rijalmyd.wanatik.data.source.firebase

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.rijalmyd.wanatik.data.model.History
import com.rijalmyd.wanatik.data.model.Motif
import com.rijalmyd.wanatik.data.model.SignInResult
import com.rijalmyd.wanatik.data.model.Store
import com.rijalmyd.wanatik.data.source.firebase.model.ProductResponse

interface FirebaseService {
    suspend fun signInGoogle(context: Context): SignInResult
    suspend fun signOut()
    suspend fun getSignedUser(): FirebaseUser?
    suspend fun getPopularProducts(): List<ProductResponse>
    suspend fun getAllProducts(): List<ProductResponse>
    suspend fun getProductsByStoreId(storeId: String): List<ProductResponse>
    suspend fun getRelatedProducts(motifName: String): List<ProductResponse>
    suspend fun getNearStores(): List<Store>
    suspend fun getStoreById(storeId: String): Store?
    suspend fun getMotifById(motifId: String): Motif?
    suspend fun clickedProduct(productId: String)
    suspend fun getDetailProduct(productId: String): ProductResponse?
    suspend fun saveToHistory(image: Uri, motifs: List<Motif?>)
    suspend fun getHistories(): List<History>
    suspend fun getDetailHistory(id: String): History?
    suspend fun searchProducts(query: String): List<ProductResponse>
}