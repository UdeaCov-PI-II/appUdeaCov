package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateEntranceResponseDto (
    var message: String
): Parcelable