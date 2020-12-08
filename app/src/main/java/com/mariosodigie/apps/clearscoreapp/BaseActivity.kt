package com.mariosodigie.apps.clearscoreapp

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.mariosodigie.apps.clearscoreapp.extensions.observe

open class BaseActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    /**
     * Subscribes to provided [ServiceError] and launches a general error dialog when an error occurs.
     * [onServiceErrorDialogAction] is invoked when user clicks dialog's action button, and
     * [onServiceErrorDialogCancelled] is invoked when dialog is cancelled.
     */
    protected fun addErrorSource(source: LiveData<ServiceError>) = source.observe(this, ::onServiceError)

    protected open fun onServiceError(error: ServiceError){
        showErrorDialog(error)
    }

    private fun showErrorDialog(error: ServiceError) {

        alertDialog = AlertDialog.Builder(this)
            .apply {
                if (error.title != null) {
                    setTitle(error.title)
                }
            }
            .setMessage(error.message)
            .setPositiveButton(R.string.dialog_button_ok) { dialog, _ ->
                onServiceErrorDialogAction(dialog, error)
            }
            .setOnCancelListener {
                onServiceErrorDialogCancelled(it, error)
            }
            .setOnDismissListener {
                onServiceErrorDialogDismissed(it, error)
                alertDialog = null
            }
            .show()
    }

    open fun onServiceErrorDialogAction(dialog: DialogInterface, error: ServiceError) = dialog.dismiss()
    open fun onServiceErrorDialogCancelled(dialog: DialogInterface, error: ServiceError) = dialog.dismiss()
    open fun onServiceErrorDialogDismissed(dialog: DialogInterface, error: ServiceError) = Unit

    override fun onStop() {
        alertDialog?.dismiss()
        super.onStop()
    }

}