package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PermissionCardResponseDto (
    var id:String?,
    var user: UserCardResponseDto?,
    var startTimeStr: String?,
    var endTimeStr: String?,
    var location: String?
): Parcelable