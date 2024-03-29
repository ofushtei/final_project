package com.example.final_project.api.helpers

import com.example.final_project.api.interfaces.ApiInterface
import com.example.final_project.api.interfaces.ConditionTypeListInterface
import com.example.final_project.models.ConditionTypeModel
import com.example.final_project.models.ErrorModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class ConditionTypeListHelper(
    private val apiInterface: ApiInterface
) : ConditionTypeListInterface {

    override fun getTypes(
        listener: ConditionTypeListInterface.ConditionTypeListListener
    ) {
        val call =
            apiInterface.listTypes()
        call.enqueue(
            object :
                retrofit2.Callback<List<ConditionTypeModel>> {
                override fun onFailure(
                    call: Call<List<ConditionTypeModel>>,
                    t: Throwable
                ) {
                    when {
                        call.isCanceled -> listener.onCancelled()
                        else -> listener.onFailure()
                    }
                }

                override fun onResponse(
                    call: Call<List<ConditionTypeModel>>,
                    response: Response<List<ConditionTypeModel>>
                ) {
                    when {
                        response.isSuccessful -> listener.onResponseSuccess(
                            list = ArrayList(
                                response.body()!!
                            )
                        )
                        else -> {
                            val errorModel: ErrorModel? =
                                try {
                                    Gson().fromJson(
                                        response.errorBody().toString(),
                                        ErrorModel::class.java
                                    )
                                } catch (e: Exception) {
                                    null
                                }
                            listener.onResponseFailure(
                                errorModel = errorModel
                            )
                        }
                    }
                }

            }
        )
    }
}