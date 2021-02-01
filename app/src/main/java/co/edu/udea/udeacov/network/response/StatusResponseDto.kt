package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusResponseDto (
    var id: String?,
    var statusId: String,
    var displayName: String,
    var action: String
): Parcelable