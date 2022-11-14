package il.co.procyonapps.bitmovie.api.responses

import il.co.procyonapps.bitmovie.api.APIConst
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizedRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${APIConst.BEARER_TOKEN}")
            .build()
        return chain.proceed(authorizedRequest)
    }
}