package com.rijalmyd.wanatik.ui.screen.scan

import android.net.Uri
import java.io.File

sealed class ScanEvent {
    data class OnScanUpload(
        val photoFile: File,
        val photoUri: Uri,
    ) : ScanEvent()
    data object ResetState : ScanEvent()
    data class OnSaveImageUri(
        val imageUri: Uri?
    ) : ScanEvent()
    data object OnCancelClassify : ScanEvent()
}
