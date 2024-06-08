package anicode.postboy.util

import android.content.Intent
import anicode.postboy.R
import anicode.postboy.view.activities.MainActivity

fun shareProject(activity: MainActivity) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = SHARE_TYPE
    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name))
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, GOOGLE_PLAY_LINK)
    activity.startActivity(Intent.createChooser(sharingIntent, "Shearing Option"))
}
