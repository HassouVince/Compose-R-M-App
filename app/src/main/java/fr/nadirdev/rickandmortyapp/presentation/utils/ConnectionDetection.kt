package fr.nadirdev.rickandmortyapp.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        ?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    } else {
            connectivityManager.activeNetworkInfo?.let {
                when(it.type){
                    ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE,
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            } ?: false
    }
}