package com.example.sustentabilidade.helpers

import android.app.Activity
import android.view.WindowManager

class ScreenHelper {

    companion object {

        fun enableLoading(activity: Activity) {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }

        fun disableLoading(activity: Activity) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }


    }
}