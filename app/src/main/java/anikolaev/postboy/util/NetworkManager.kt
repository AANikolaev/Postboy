package anikolaev.postboy.util

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
class NetworkManager(context: Context) {

    private val connectivityService = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable(): Boolean = connectivityService.activeNetworkInfo != null &&
            (connectivityService.activeNetworkInfo.isAvailable ||
                    connectivityService.activeNetworkInfo.isConnectedOrConnecting)
}