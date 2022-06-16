package com.artech.requestsappandroid.ui.screens.task_details.models

sealed class TaskDetailsViewEvent {
    object EnterScreen : TaskDetailsViewEvent()
    object AddParts : TaskDetailsViewEvent()
    object CompleteTask : TaskDetailsViewEvent()
}