package com.thienphu.mytodolistapp.utils

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION

}


fun stringToAction(string:String?) : Action {
    return when {
        string == "ADD" -> {
            Action.ADD
        }
        string == "UPDATE" ->{
            Action.UPDATE
        }
        string == "DELETE" ->{
            Action.DELETE
        }
        string == "DELETE_ALL" ->{
            Action.DELETE_ALL
        }
        string == "UNDO"->{
            Action.UNDO
        }
        else ->{
            Action.NO_ACTION
        }
    }
}