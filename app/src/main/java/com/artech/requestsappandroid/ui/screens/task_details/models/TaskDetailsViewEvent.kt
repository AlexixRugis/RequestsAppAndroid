package com.artech.requestsappandroid.ui.screens.task_details.models

sealed class TaskDetailsViewEvent {
    object EnterScreen : TaskDetailsViewEvent()
    object AddParts : TaskDetailsViewEvent()
    object CompleteTask : TaskDetailsViewEvent()
    object LoadRepairParts : TaskDetailsViewEvent()
    data class SelectedPartAmountChanged(val amount: Int) : TaskDetailsViewEvent()
    object SubmitPartAdding : TaskDetailsViewEvent()
    data class SelectRepairPart(val id: Int) : TaskDetailsViewEvent()
}