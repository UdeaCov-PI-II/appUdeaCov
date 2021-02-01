package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApprovalsResponseDto (
    var id: String?,
    var user: UserResponseDto,
    var reason: String?,
    var approved: Boolean
): Parcelable