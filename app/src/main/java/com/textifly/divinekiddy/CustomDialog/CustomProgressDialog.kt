package com.textifly.divinekiddy.CustomDialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.textifly.divinekiddy.R


class CustomProgressDialog {
    companion object{
        private var mProgressdialog: Dialog? = null
        fun  showDialog(mContext: Context, dialog_view: Boolean) {
            if (dialog_view) {
                showdialog(mContext)
            } else if (!dialog_view) {
                try {
                    mProgressdialog?.dismiss()
                } catch (e: Exception) {
                }
            }
        }

        private fun showdialog(mContext: Context) {
            mProgressdialog = Dialog(mContext)
            mProgressdialog!!.setContentView(R.layout.layout_progress_dialog)
            mProgressdialog!!.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val window: Window? = mProgressdialog!!.window
            val wlp: WindowManager.LayoutParams = window!!.attributes
            wlp.gravity = Gravity.NO_GRAVITY
            mProgressdialog!!.setCancelable(false)
            mProgressdialog!!.show()
        }
    }

}