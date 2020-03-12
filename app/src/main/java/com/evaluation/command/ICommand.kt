package com.evaluation.command

interface ICommand<T> {
    fun execute(param: T)
}