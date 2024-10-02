package com.aaron.advancebookings.di.appresources

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class GetAppResources {
    abstract fun getString(@StringRes stringResId:Int):String
    abstract fun getStringList(@StringRes stringResId:Int):Array<String>
    abstract fun getDrawable(@DrawableRes drawableResId:Int):Drawable?
    abstract fun getAssets(assetFileName:String):Any
}