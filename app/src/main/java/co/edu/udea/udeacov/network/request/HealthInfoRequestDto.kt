package co.edu.udea.udeacov.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HealthInfoRequestDto (
    var weight: Float = 0f ,
    var height: Float = 0f,
    var hasHighBloodPressure: Boolean = false,
    var hasHeartDisease: Boolean = false,
    var hasHighCholesterol: Boolean = false,
    var haskidneyDisease: Boolean = false,
    var hasDiabetes: Boolean = false,
    var hasEPOC: Boolean = false,
    var hasAsthma: Boolean = false,
    var hasAlterationImmunityDisease: Boolean = false,
    var hasCancer: Boolean = false,
    var useOralSteroids: Boolean = false,
    var hasHepaticDisease: Boolean = false,
    var hasObesity: Boolean = false,
    var isSmoker: Boolean = false,
    var hasStrangeDisease: Boolean = false,
    var isPregnant: Boolean = false,
    var hasChildBirthRecently: Boolean = false,
    var hasPermanentDisability: String = "",
    var bloodType: String = "",
    var hasRoomates: Boolean = false,
    var roomatesConditions: ArrayList<String> = ArrayList()
): Parcelable