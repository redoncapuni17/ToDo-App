package com.example.todolist.domain.presentation.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun mySnackbar(                     //  Snackbar, bir kullanıcı arayüzü öğesidir ve genellikle ekranın alt kısmında kısa süreli gösterilen bildirimler
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    msg: String,           //  Snackbar içerisinde gösterilecek metin.
    actionLabel : String,
    onAction:()->Unit     //    Snackbar üzerindeki eylem etiketine tıklandığında gerçekleştirilecek işlevi temsil eden bir lambda fonksiyonu
){
    scope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()    // Eğer mevcut bir Snackbar varsa, onu kapatır.
        val snackbarResult:SnackbarResult = snackbarHostState.showSnackbar(
            message = msg,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Short
        )
        when(snackbarResult){
            SnackbarResult.ActionPerformed->{
                onAction()
            }
            SnackbarResult.Dismissed->{

            }
        }
    }
}