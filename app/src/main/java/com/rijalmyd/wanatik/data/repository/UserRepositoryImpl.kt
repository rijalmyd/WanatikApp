package com.rijalmyd.wanatik.data.repository

import android.content.Context
import android.net.Uri
import com.rijalmyd.wanatik.data.model.Motif
import com.rijalmyd.wanatik.data.model.Product
import com.rijalmyd.wanatik.data.model.Store
import com.rijalmyd.wanatik.data.source.Result
import com.rijalmyd.wanatik.data.source.firebase.FirebaseService
import com.rijalmyd.wanatik.data.source.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val firebaseService: FirebaseService,
) : UserRepository {

    override fun signInGoogle(context: Context) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.signInGoogle(context)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error("Terjadi Kesalahan"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getSignedUser() = flow {
        emit(firebaseService.getSignedUser())
    }.flowOn(Dispatchers.IO)

    override suspend fun signOut() {
        firebaseService.signOut()
    }

    override fun classify(image: File, imageUri: Uri) = flow {
        val user = firebaseService.getSignedUser()
        emit(
            Result.Loading(
                message = "Sedang mengunggah gambar",
                title = "1/${if (user != null) "3" else "2"}"
            )
        )
        try {
            val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "file",
                image.name,
                requestImageFile
            )
            val result = apiService.classify(imageMultipart)

            emit(
                Result.Loading(
                    message = "Sedang melakukan klasifikasi pada gambar",
                    title = "2/${if (user != null) "3" else "2"}"
                )
            )
            val classify = result.predictions
                .filter { (it.confidence ?: 0.0) >= 0.1 }
                .map { item ->
                    firebaseService.getMotifById(item.classDetected?.replace(" ", "_")?.lowercase(Locale.ROOT) ?: "")
                        ?.copy(
                            confidence = item.confidence ?: 0.0
                        )
                }

            if (user != null) {
                emit(Result.Loading(message = "Menyimpan riwayat", title = "3/3"))
                firebaseService.saveToHistory(imageUri, classify)
            }

            if (classify.isEmpty()) {
                emit(Result.Error("Gambar tidak terklasifikasi"))
            }

            emit(Result.Success(classify))
        } catch (e: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getPopularProducts() = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getPopularProducts().map {
                Product(
                    id = it.id,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    description = it.description,
                    price = it.price,
                    discount = it.discount,
                    motifName = it.motifName,
                    store = firebaseService.getStoreById(it.storeId) ?: Store()
                )
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllProducts() = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getAllProducts().map {
                Product(
                    id = it.id,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    description = it.description,
                    price = it.price,
                    discount = it.discount,
                    motifName = it.motifName,
                    store = firebaseService.getStoreById(it.storeId) ?: Store()
                )
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getProductsByStoreId(storeId: String) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getProductsByStoreId(storeId).map {
                Product(
                    id = it.id,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    description = it.description,
                    price = it.price,
                    discount = it.discount,
                    motifName = it.motifName,
                    store = firebaseService.getStoreById(it.storeId) ?: Store()
                )
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getNearStores() = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getNearStores()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getRelatedProducts(classDetected: String) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getRelatedProducts(classDetected).map {
                Product(
                    id = it.id,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    description = it.description,
                    price = it.price,
                    discount = it.discount,
                    motifName = it.motifName,
                    store = firebaseService.getStoreById(it.storeId) ?: Store()
                )
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun clickedProduct(productId: String) {
        try {
            firebaseService.clickedProduct(productId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getDetailProduct(productId: String) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getDetailProduct(productId)?.run {
                Product(
                    id = id,
                    title = title,
                    imageUrl = imageUrl,
                    description = description,
                    price = price,
                    discount = discount,
                    motifName = motifName,
                    store = firebaseService.getStoreById(storeId) ?: Store()
                )
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun saveToHistory(image: Uri, motifs: List<Motif?>) = flow {
        emit(Result.Loading())
        try {
            firebaseService.saveToHistory(image, motifs)
            emit(Result.Success(null))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getHistories() = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getHistories()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getDetailHistory(id: String) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getDetailHistory(id)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMotifById(id: String) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.getMotifById(id)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchProducts(query: String) = flow {
        emit(Result.Loading())
        try {
            val result = firebaseService.searchProducts(query).map {
                Product(
                    id = it.id,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    description = it.description,
                    price = it.price,
                    discount = it.discount,
                    motifName = it.motifName,
                    store = firebaseService.getStoreById(it.storeId) ?: Store()
                )
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}