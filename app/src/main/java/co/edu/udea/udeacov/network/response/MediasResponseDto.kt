package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediasResponseDto (
    var id: String?,
    var originalName: String,
    var name: String,
    var dowloadUrl: String,
    var type: String
): Parcelable