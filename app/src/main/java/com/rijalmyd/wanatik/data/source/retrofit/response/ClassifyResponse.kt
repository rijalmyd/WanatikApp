package com.rijalmyd.wanatik.data.source.retrofit.response

import com.google.gson.annotations.SerializedName

data class ClassifyResponse(

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem>
)

data class PredictionsItem(

	@field:SerializedName("confidence")
	val confidence: Double? = null,

	@field:SerializedName("class")
	val classDetected: String? = null
)
