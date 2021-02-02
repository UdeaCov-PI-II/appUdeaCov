package co.edu.udea.udeacov.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EntranceRequestDto (
var permissionId: String = "",
var temperature:String = "",
var entry: Boolean = false
): Parcelable