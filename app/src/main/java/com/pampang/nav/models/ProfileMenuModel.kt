package com.pampang.nav.models

data class ProfileMenuModel(
    val title: String,
    val iconResId: Int? = null,
    val subMenuItems: List<ProfileMenuModel> = emptyList(),
    var isExpanded: Boolean = false
)

val profileMenus = listOf(
    ProfileMenuModel(
        title = "Personal Detail (Edit Username)",
    ),
    ProfileMenuModel(
        title = "Contact Us",
    ),
    ProfileMenuModel(
        title = "Privacy and Security",
        subMenuItems = listOf(
            ProfileMenuModel(title = "Change Password")
        )
    ),
    ProfileMenuModel(
        title = "Preferences",
    ),
    ProfileMenuModel(
        title = "Logout",
    ),
)
