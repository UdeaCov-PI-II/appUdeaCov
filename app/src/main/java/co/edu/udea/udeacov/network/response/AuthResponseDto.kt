package co.edu.udea.udeacov.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthResponseDto (
   var token : String,
   var type : String,
   var userId : String,
   var username: String,
   var roles : List<String>
) : Parcelable