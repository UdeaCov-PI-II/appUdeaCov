package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponseDto (
var id: String?,
var fullName: String,
var username: String,
var role: RoleResponseDto
): Parcelable