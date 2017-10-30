package com.peppersquaretestapp.commons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by smit on 29/10/17.
 */
fun ViewGroup.inflate(layoutId : Int) : View
{
    return LayoutInflater.from(context).inflate(layoutId,this, false)
}