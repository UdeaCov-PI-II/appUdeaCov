package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserCardResponseDto (
    var id: String,
    var fullName: String,
    var documentType: String,
    var documentNumber: String
): Parcelable
