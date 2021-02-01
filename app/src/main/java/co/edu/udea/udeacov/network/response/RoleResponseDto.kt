package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoleResponseDto (
    var modelId: String,
    var creationTime: String?,
    var roleId: String,
    var displayName: String
): Parcelable