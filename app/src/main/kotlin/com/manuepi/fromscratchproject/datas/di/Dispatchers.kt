package com.manuepi.fromscratchproject.datas.di

object DispatchersNames {
    const val UI_LAYOUT = "DISPATCHER_UI_LAYOUT"
    const val UI_VIEW_MODEL = "DISPATCHER_UI_VIEW_MODEL"
    const val DOMAIN = "DISPATCHER_DOMAIN"
    const val DATA = "DISPATCHER_DATA"
    const val ENTITY = "DISPATCHER_ENTITY"
}
object ScopesName {
    // During a logged session, if user loggout : clear children jobs
    const val PROFILE_SESSION_SCOPE = "PROFILE_SESSION_SCOPE"

    // Exists as long as the app lives
    const val ALWAYS_ALIVE_APP_SCOPE = "ALWAYS_ALIVE_APP_SCOPE"
}