package com.aaron.advancebookings.di.appresources

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppResources @Inject constructor(
    @ApplicationContext private val context: Context
): GetAppResources(){
    override fun getString(@StringRes stringResId: Int): String {
        return context.resources.getString(stringResId)
    }

    override fun getStringList(@ArrayRes stringResId: Int): Array<String> {
        return context.resources.getStringArray(stringResId)
    }

    override fun getDrawable(@DrawableRes drawableResId: Int): Drawable? {
        return ContextCompat.getDrawable(context,drawableResId)

    }

    override fun getAssets(assetFileName: String): Any {
        return context.assets.open(assetFileName)
    }


}