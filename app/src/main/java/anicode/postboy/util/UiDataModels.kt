package anicode.postboy.util

data class ProgressDialogModel(
    var isProgressDialogNeeded: Boolean,
    var text: String? = null
)

data class ErrorDialogModel(var errorTitle: String? = null,
                            var errorMessage: String? = null)