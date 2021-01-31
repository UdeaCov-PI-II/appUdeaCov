package co.edu.udea.udeacov.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PermissionRequestDto (
    var userId : String="",
    var startTimeStr: String="",
    var endTimeStr: String="",
    var statusId: String="",
    var managerUnitId: String="",
    var reason: String="",
    var locationId: String="",
    var building: String="",
    var officeNumber: String="",
    var locationOutOfUniversity: String="",
    var requestedDays: MutableList<String> = mutableListOf(),
    var requestedWorkingDay: String=""
): Parcelable