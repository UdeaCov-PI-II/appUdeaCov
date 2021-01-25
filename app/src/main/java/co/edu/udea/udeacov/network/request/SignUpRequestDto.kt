package co.edu.udea.udeacov.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpRequestDto (
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var documentType: String = "",
    var documentNumber: String = "",
    var fullName: String = "",
    var birthday: String = "",
    var personalEmail: String = "",
    var arlName: String = "",
    var phoneContact: String = "",
    var town: String = "",
    var universityInfo: UniversityInfoRequestDto = UniversityInfoRequestDto(),
    var healthInfo: HealthInfoRequestDto = HealthInfoRequestDto(),
    var roleId: String = ""
): Parcelable
