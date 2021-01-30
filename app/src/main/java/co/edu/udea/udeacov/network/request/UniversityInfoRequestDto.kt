package co.edu.udea.udeacov.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UniversityInfoRequestDto (
    var universityRelation: MutableList<String> = mutableListOf(),
    var detailUniversityRelation: String = "",
    var occupation: String= "",
    var buildingAndOffice: String = "",
    var unitId: String = "",
    var locationId: String = "",
    var belongToExtensionProject: Boolean = false,
    var extensionProjectName: String = "",
    var transportationMode: String = ""
): Parcelable
