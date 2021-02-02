package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PermissionResponseDto (
    var id: String?,
    var user: UserResponseDto?,
    var startTimeStr: String?,
    var endTimeStr: String?,
    var location: String?,
    var reason : String?,
    var status: StatusResponseDto?,
    var medias: MutableList<MediasResponseDto?>? = mutableListOf(),
    var approvals: MutableList<ApprovalsResponseDto>? = mutableListOf(),
    var entrance: EntranceResponseDto?
): Parcelable