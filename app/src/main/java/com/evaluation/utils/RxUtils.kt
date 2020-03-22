/*
 * Copyright ( c ) 2017, 3ScreenSolutions GmbH & Co. KG, All Rights Reserved.
 */
package com.evaluation.utils

import io.reactivex.disposables.Disposable

/**
 * Utility class for RX related operations.
 *
 * @author Barabas Attila
 * @since 2018.07.02
 */
object RxUtils {
    /**
     * Dispose the given RX observable without logs.
     */
    fun disposeSilently(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            try {
                disposable.dispose()
            } catch (e: Exception) {
                // Ignore
            }
        }
    }
}