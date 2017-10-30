package com.peppersquaretestapp.commons

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ProgressBar
import com.peppersquaretestapp.R

/**
 * Created by smit on 29/10/17.
 */
open class BaseFragment : Fragment() {

    fun showProgressBar(progressBar : ProgressBar)
    {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(progressBar : ProgressBar)
    {
        progressBar.visibility = View.INVISIBLE
    }

    fun showAlertDialog(message : String)
    {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setTitle(getString(R.string.app_name))
        alertBuilder.setMessage(message)
        alertBuilder.setCancelable(false)
        alertBuilder.setNeutralButton("Ok") { dialog, _ -> dialog.dismiss() }

        val alertDialog = alertBuilder.create()
        alertDialog.show()
    }

    // Check Internet connection
    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }

}