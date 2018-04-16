package com.example.whiteshadow.hat_api.Managers

import android.net.UrlQuerySanitizer
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

interface NetworkLayer {

    fun getRequest(url: String, parameters: List<Pair<String, Any?>>? = null, headers: List<Pair<String, String>>, completion: (r: ResultType?) -> Unit)
    fun getRequestString(url: String, parameters: List<Pair<String, Any?>>? = null, headers: List<Pair<String, String>>, completion: (r: ResultType?) -> Unit)
}

enum class ResultType(var statusCode: Int?, var error: Error?, var json: List<Map<String, Any>>?, var resultString: String?, var token: String?){

    IsSuccess(null, null, null, null, null),
    HasFailed(null, null, null,null, null);
}

class HATNetworkManager: NetworkLayer {

    var resultType: ResultType? = null

    override fun getRequest(url: String , parameters: List<Pair<String, Any?>>?, headers: List<Pair<String, String>>, completion: (r: ResultType?) -> Unit) {

        url.httpGet(parameters).responseJson{ request, response, result ->

            //do something with response
            when (result) {

                is Result.Failure -> {

                    val ex = result.getException()
                    val error = Error(ex)

                    var test = ResultType.HasFailed
                    test.statusCode = response.statusCode
                    test.error = error
                    test.json = null
                    test.resultString = null
                    test.token = null

                    resultType = test
                    completion(resultType)
                }
                is Result.Success -> {

                    val token = response.headers["x-auth-token"]?.first()
                    val test2 = result.component1() as? List<Map<String, Any>>?

                    var test = ResultType.IsSuccess
                    test.statusCode = response.statusCode
                    test.error = null
                    test.json = test2
                    test.resultString = null
                    test.token = token


                    resultType = test
                    completion(resultType)
                }
            }
        }
    }

    override fun getRequestString(url: String , parameters: List<Pair<String, Any?>>?, headers: List<Pair<String, String>>, completion: (r: ResultType?) -> Unit) {

        url.httpGet(parameters).responseString{request, response, result ->

            //do something with response
            when (result) {

                is Result.Failure -> {

                    val ex = result.getException()
                    val error = Error(ex)

                    var test = ResultType.HasFailed
                    test.statusCode = response.statusCode
                    test.error = error
                    test.json = null
                    test.resultString = null
                    test.token = null

                    resultType = test
                    completion(resultType)
                }
                is Result.Success -> {

                    val token = response.headers["x-auth-token"]?.first()
                    val test2 = result.component1()

                    var test = ResultType.IsSuccess
                    test.statusCode = response.statusCode
                    test.error = null
                    test.json = null
                    test.resultString = test2
                    test.token = token


                    resultType = test
                    completion(resultType)
                }
            }
        }
    }

    fun getQueryStringParameter(url: String?, param: String): String? {

        if (url != null) {

            val sanititizer = UrlQuerySanitizer()
            sanititizer.parseUrl(url)
            val parameters = sanititizer.parameterList

            for (item in parameters) {

                if (item.mParameter == param) {

                    return item.mValue
                }
            }
        }

        return null
    }
}