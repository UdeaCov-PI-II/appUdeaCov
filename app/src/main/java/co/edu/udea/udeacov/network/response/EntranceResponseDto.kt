package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EntranceResponseDto (
    var id: String,
    var permissionId: String,
    var entryTimeStr: String,
    var entryTemperature: String,
    var departureTimeStr: String,
    var departureTemperature: String
): Parcelable