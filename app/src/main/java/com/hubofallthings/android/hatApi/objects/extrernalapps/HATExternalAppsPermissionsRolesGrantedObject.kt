package com.hubofallthings.android.hatApi.objects.extrernalapps

import java.io.Serializable

data class HATExternalAppsPermissionsRolesGrantedObject(

        // MARK: - Variables
        /// The permission role, read, write etc
        val role: String = "",
                /// The app the role applies to
        val detail: String? = null
): Serializable